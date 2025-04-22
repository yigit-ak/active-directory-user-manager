package net.yigitak.ad_user_manager.services;

import net.yigitak.ad_user_manager.dto.UserCreateDto;
import net.yigitak.ad_user_manager.dto.UserResponseDto;
import net.yigitak.ad_user_manager.exceptions.AccountControlFetchException;
import net.yigitak.ad_user_manager.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.Arrays;

import static net.yigitak.ad_user_manager.util.DnExtractor.extractFirstOu;
import static net.yigitak.ad_user_manager.util.PasswordEncoder.encodePassword;
import static net.yigitak.ad_user_manager.util.SecurePasswordGenerator.generatePassword;
import static net.yigitak.ad_user_manager.util.UserAccountControlUtil.*;
import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Service
public class UserService {

    @Value("${parent-organizational-unit}")
    private String PARENT_ORGANIZATIONAL_UNIT;

    @Value("${ldap.base}")
    private String LDAP_BASE;

    @Value("${user-creation-description}")
    private String DESCRIPTION;

    @Autowired
    private LdapTemplate ldapTemplate;

    @Autowired
    private EmailService emailService;

    public UserResponseDto findUserByCn(String commonName) {
        LdapQuery query = query()
                .base("ou=%s".formatted(PARENT_ORGANIZATIONAL_UNIT)) // Use base DN dynamically
                .where("cn").is(commonName); // Search for the user by 'cn'

        // Use an AttributesMapper to map LDAP attributes to UserDto
        return ldapTemplate.search(query, (AttributesMapper<UserResponseDto>) attributes -> new UserResponseDto(
                extractFirstOu(attributes.get("distinguishedName").get().toString()),
                attributes.get("cn").get().toString(),
                attributes.get("sAMAccountName").get().toString(),
                attributes.get("displayName").get().toString(),
                attributes.get("givenName").get().toString(),
                attributes.get("sn").get().toString(),
                attributes.get("mail").get().toString(),
                attributes.get("telephoneNumber").get().toString(),
                isAccountEnabled(attributes.get("userAccountControl").get().toString())
        )).stream().findFirst().orElse(null); // Assuming only one user should match, return null if none found
    }

    public void createNewUser(UserCreateDto user) {
        String fullName = "%s %s".formatted(user.firstName(), user.lastName()); // what if 2 names or 2 surnames
        String commonName = "s@%s.%s".formatted(user.firstName(), user.lastName());

        Name dn = LdapNameBuilder.newInstance()
                .add("OU", PARENT_ORGANIZATIONAL_UNIT)
                .add("OU", user.vendor())
                .add("CN", commonName) // Common Name
                .build();

        DirContextAdapter context = new DirContextAdapter(dn); // new LDAP context for the user entry

        // object classes for the user
        context.setAttributeValues("objectClass", new String[]{
                "top",
                "person",
                "organizationalPerson",
                "user"
        });

        String pw = generatePassword();

        context.setAttributeValue("cn", commonName);
        context.setAttributeValue("description", DESCRIPTION);
        context.setAttributeValue("sAMAccountName", "%s.%s".formatted(user.firstName(), user.lastName())); // TODO: change
        context.setAttributeValue("displayName", fullName);
        context.setAttributeValue("givenName", user.firstName());
        context.setAttributeValue("mail", user.email());
        context.setAttributeValue("sn", user.lastName());
        context.setAttributeValue("telephoneNumber", user.phoneNumber());
        context.setAttributeValue("unicodePwd", encodePassword(pw));
        context.setAttributeValue("userPrincipalName", "%s.%s@yigit.local".formatted(user.firstName(), user.lastName())); // TODO: change

        ldapTemplate.bind(context);

        unlockUser(commonName);

        emailService.sendAccountCreationMail(user.email(), pw);
    }

    public void resetPassword(String commonName) {

        UserResponseDto user = findUserByCn(commonName);
        if (user == null) {
            throw new UserNotFoundException(commonName);
        }

        // Construct the complete DN including the vendor OU
        Name dn = LdapNameBuilder.newInstance()
                .add("OU", PARENT_ORGANIZATIONAL_UNIT)
                .add("OU", user.vendor())  // Add vendor OU
                .add("CN", commonName)
                .build();

        String newPassword = generatePassword();

        ModificationItem[] mods = new ModificationItem[]{
                new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("unicodePwd", encodePassword(newPassword)))
        };

        ldapTemplate.modifyAttributes(dn, mods);

        System.out.println("Attributes are modified: ");

        emailService.sendPasswordResetMail(user.email(), newPassword);

        System.out.println("Password is sent to the user: " + user.email());
    }

    public void lockUser(String commonName) {
        // First, get the complete DN using findUserByCn
        UserResponseDto user = findUserByCn(commonName);
        if (user == null) {
            throw new UserNotFoundException(commonName);
        }

        // Construct the complete DN including the vendor OU
        Name dn = LdapNameBuilder.newInstance()
                .add("OU", PARENT_ORGANIZATIONAL_UNIT)
                .add("OU", user.vendor())  // Add vendor OU
                .add("CN", commonName)
                .build();

        // Get current UAC value
        LdapQuery query = query()
                .base(dn.toString())
                .where("cn").is(commonName);

        String userAccountControl = ldapTemplate.search(query, (
                        AttributesMapper<String>) attributes ->
                        attributes.get("userAccountControl").get().toString()
                ).stream().findFirst()
                .orElseThrow(() -> new AccountControlFetchException(commonName));

        // Calculate new UAC value
        String newUserAccountControl = String.valueOf(disableAccount(userAccountControl));

        // Modify the attributes
        ldapTemplate.modifyAttributes(dn, new ModificationItem[]{
                new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                        new BasicAttribute("userAccountControl", newUserAccountControl))
        });
    }

    public void unlockUser(String commonName) {
        // First, get the complete DN using findUserByCn
        UserResponseDto user = findUserByCn(commonName);
        if (user == null) {
            throw new UserNotFoundException(commonName);
        }

        // Construct the complete DN including the vendor OU
        Name dn = LdapNameBuilder.newInstance()
                .add("OU", PARENT_ORGANIZATIONAL_UNIT)
                .add("OU", user.vendor())  // Add vendor OU
                .add("CN", commonName)
                .build();

        // Get current UAC value
        LdapQuery query = query()
                .base(dn.toString())
                .where("cn").is(commonName);

        String userAccountControl = ldapTemplate.search(query, (
                        AttributesMapper<String>) attributes ->
                        attributes.get("userAccountControl").get().toString()
                ).stream().findFirst()
                .orElseThrow(() -> new AccountControlFetchException(commonName));

        // Calculate new UAC value
        String newUserAccountControl = String.valueOf(enableUser(userAccountControl));

        // Modify the attributes
        ldapTemplate.modifyAttributes(dn, new ModificationItem[]{
                new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                        new BasicAttribute("userAccountControl", newUserAccountControl))
        });
    }
}
