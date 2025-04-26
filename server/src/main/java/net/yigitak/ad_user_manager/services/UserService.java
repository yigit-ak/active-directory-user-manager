package net.yigitak.ad_user_manager.services;

import net.yigitak.ad_user_manager.dto.UserCreateDto;
import net.yigitak.ad_user_manager.dto.UserResponseDto;
import net.yigitak.ad_user_manager.exceptions.AccountControlFetchException;
import net.yigitak.ad_user_manager.exceptions.UserNotFoundException;
import net.yigitak.ad_user_manager.enums.ActionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;

import static net.yigitak.ad_user_manager.util.DnExtractor.extractFirstOu;
import static net.yigitak.ad_user_manager.util.PasswordEncoder.encodePassword;
import static net.yigitak.ad_user_manager.util.SecurePasswordGenerator.generatePassword;
import static net.yigitak.ad_user_manager.util.UserAccountControlUtil.*;
import static org.springframework.ldap.query.LdapQueryBuilder.query;

/**
 * Service for managing users in Active Directory via LDAP operations.
 * <p>
 * Provides methods for creating users, resetting passwords, locking and unlocking accounts,
 * as well as logging user actions.
 */
@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Value("${parent-organizational-unit}")
    private String PARENT_ORGANIZATIONAL_UNIT;

    @Value("${ldap.base}")
    private String LDAP_BASE;

    @Value("${user-creation-description}")
    private String DESCRIPTION;

    private final LdapTemplate ldapTemplate;
    private final EmailService emailService;
    private final ActionLogService actionLogService;

    /**
     * Constructs a UserService with required dependencies.
     *
     * @param ldapTemplate      the LDAP template for performing LDAP operations
     * @param emailService      the service for sending emails
     * @param actionLogService  the service for logging user actions
     */
    public UserService(LdapTemplate ldapTemplate, EmailService emailService, ActionLogService actionLogService) {
        this.ldapTemplate = ldapTemplate;
        this.emailService = emailService;
        this.actionLogService = actionLogService;
    }

    /**
     * Finds a user in Active Directory by their common name (CN).
     *
     * @param commonName the CN of the user
     * @return the user details as {@link UserResponseDto}
     * @throws UserNotFoundException if the user is not found
     */
    public UserResponseDto findUserByCn(String commonName) {
        logger.debug("Searching user with CN={}", commonName);

        LdapQuery query = query().base("ou=%s".formatted(PARENT_ORGANIZATIONAL_UNIT)).where("cn").is(commonName);

        return ldapTemplate.search(query, (AttributesMapper<UserResponseDto>) attributes -> new UserResponseDto(extractFirstOu(attributes.get("distinguishedName").get().toString()), attributes.get("cn").get().toString(), attributes.get("sAMAccountName").get().toString(), attributes.get("displayName").get().toString(), attributes.get("givenName").get().toString(), attributes.get("sn").get().toString(), attributes.get("mail").get().toString(), attributes.get("telephoneNumber").get().toString(), isAccountEnabled(attributes.get("userAccountControl").get().toString()))).stream().findFirst().orElseThrow(() -> new UserNotFoundException(commonName));
    }


    /**
     * Creates a new user in Active Directory with a generated password.
     * Sends an account creation email and logs the action.
     *
     * @param user the user data to create
     */
    public void createNewUser(UserCreateDto user) {
        logger.info("Creating new user: firstName={}, lastName={}, vendor={}", user.firstName(), user.lastName(), user.vendor());

        String fullName = "%s %s".formatted(user.firstName(), user.lastName());
        String commonName = "s@%s.%s".formatted(user.firstName(), user.lastName());

        Name dn = LdapNameBuilder.newInstance().add("OU", PARENT_ORGANIZATIONAL_UNIT).add("OU", user.vendor()).add("CN", commonName).build();

        DirContextAdapter context = new DirContextAdapter(dn);

        context.setAttributeValues("objectClass", new String[]{"top", "person", "organizationalPerson", "user"});

        String pw = generatePassword();

        context.setAttributeValue("cn", commonName);
        context.setAttributeValue("description", DESCRIPTION);
        context.setAttributeValue("sAMAccountName", "%s.%s".formatted(user.firstName(), user.lastName()));
        context.setAttributeValue("displayName", fullName);
        context.setAttributeValue("givenName", user.firstName());
        context.setAttributeValue("mail", user.email());
        context.setAttributeValue("sn", user.lastName());
        context.setAttributeValue("telephoneNumber", user.phoneNumber());
        context.setAttributeValue("unicodePwd", encodePassword(pw));
        context.setAttributeValue("userPrincipalName", user.email());

        ldapTemplate.bind(context);
        logger.info("LDAP entry created for user: {}", commonName);

        unlockUser(commonName);
        logger.info("User account unlocked after creation: {}", commonName);

        emailService.sendAccountCreationMail(user.email(), pw);
        logger.info("Account creation email sent to: {}", user.email());
        actionLogService.logAction(ActionType.CREATE_USER, commonName);
    }

    /**
     * Resets the password of an existing user, sends the new password by email, and logs the action.
     *
     * @param commonName the CN of the user
     * @throws UserNotFoundException if the user is not found
     */
    public void resetPassword(String commonName) {
        logger.info("Resetting password for user: {}", commonName);

        UserResponseDto user = findUserByCn(commonName);

        Name dn = LdapNameBuilder.newInstance().add("OU", PARENT_ORGANIZATIONAL_UNIT).add("OU", user.vendor()).add("CN", commonName).build();

        String newPassword = generatePassword();

        ModificationItem[] mods = new ModificationItem[]{new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("unicodePwd", encodePassword(newPassword)))};

        ldapTemplate.modifyAttributes(dn, mods);
        logger.info("Password attribute updated in LDAP for user: {}", commonName);

        emailService.sendPasswordResetMail(user.email(), newPassword);
        logger.info("Password reset email sent to user: {}", user.email());
        actionLogService.logAction(ActionType.RESET_PASSWORD, commonName);
    }

    /**
     * Locks (disables) a user account in Active Directory and logs the action.
     *
     * @param commonName the CN of the user to lock
     * @throws UserNotFoundException if the user is not found
     */
    public void lockUser(String commonName) {
        logger.info("Locking user account: {}", commonName);

        UserResponseDto user = findUserByCn(commonName);

        Name dn = LdapNameBuilder.newInstance().add("OU", PARENT_ORGANIZATIONAL_UNIT).add("OU", user.vendor()).add("CN", commonName).build();

        LdapQuery query = query().base(dn.toString()).where("cn").is(commonName);

        String userAccountControl = ldapTemplate.search(query, (AttributesMapper<String>) attributes -> attributes.get("userAccountControl").get().toString()).stream().findFirst().orElseThrow(() -> new AccountControlFetchException(commonName));

        String newUserAccountControl = String.valueOf(disableAccount(userAccountControl));

        ldapTemplate.modifyAttributes(dn, new ModificationItem[]{new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("userAccountControl", newUserAccountControl))});

        logger.info("User account locked successfully: {}", commonName);
        actionLogService.logAction(ActionType.LOCK_USER, commonName);
    }


    /**
     * Unlocks (enables) a previously disabled user account in Active Directory and logs the action.
     *
     * @param commonName the CN of the user to unlock
     * @throws UserNotFoundException if the user is not found
     */
    public void unlockUser(String commonName) {
        logger.info("Unlocking user account: {}", commonName);

        UserResponseDto user = findUserByCn(commonName);

        Name dn = LdapNameBuilder.newInstance().add("OU", PARENT_ORGANIZATIONAL_UNIT).add("OU", user.vendor()).add("CN", commonName).build();

        LdapQuery query = query().base(dn.toString()).where("cn").is(commonName);

        String userAccountControl = ldapTemplate.search(query, (AttributesMapper<String>) attributes -> attributes.get("userAccountControl").get().toString()).stream().findFirst().orElseThrow(() -> new AccountControlFetchException(commonName));

        String newUserAccountControl = String.valueOf(enableUser(userAccountControl));

        ldapTemplate.modifyAttributes(dn, new ModificationItem[]{new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("userAccountControl", newUserAccountControl))});

        logger.info("User account unlocked successfully: {}", commonName);
        actionLogService.logAction(ActionType.UNLOCK_USER, commonName);
    }
}
