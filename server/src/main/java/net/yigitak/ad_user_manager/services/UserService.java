package net.yigitak.ad_user_manager.services;

import net.yigitak.ad_user_manager.dto.UserCreateDto;
import net.yigitak.ad_user_manager.dto.UserResponseDto;
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
    private PasswordEncoderService encoderService;

    public UserResponseDto findUserByCn(String cn) {
        LdapQuery query = query()
                .base("ou=%s".formatted(PARENT_ORGANIZATIONAL_UNIT)) // Use base DN dynamically
                .where("cn").is(cn); // Search for the user by 'cn'

        // Use an AttributesMapper to map LDAP attributes to UserDto
        return ldapTemplate.search(query, (AttributesMapper<UserResponseDto>) attributes -> {
            return new UserResponseDto(
                    (String) attributes.get("cn").get(),
                    (String) attributes.get("givenName").get(),
                    (String) attributes.get("sn").get(),
                    (String) attributes.get("displayName").get(),
                    (String) attributes.get("mail").get(),
                    (String) attributes.get("telephoneNumber").get(),
                    (String) attributes.get("sAMAccountName").get(),
                    (String) attributes.get("userPrincipalName").get()
                    // TODO: add vendor
                    );
        }).stream().findFirst().orElse(null); // Assuming only one user should match, return null if none found
    }

    public void createNewUser(UserCreateDto user) {
        System.out.println("\u001B[34m" +
                "CREATING NEW USER" +
                "\u001B[0m");
        String fullName = "%s %s".formatted(user.firstName(), user.lastName());

        Name dn = LdapNameBuilder.newInstance()
                .add("OU", PARENT_ORGANIZATIONAL_UNIT)
                .add("OU", user.vendor())
                .add("CN", "s@%s.%s".formatted(user.firstName(), user.lastName())) // Common Name
                .build();

        DirContextAdapter context = new DirContextAdapter(dn); // new LDAP context for the user entry

        // object classes for the user
        context.setAttributeValues("objectClass", new String[]{
                "top",
                "person",
                "organizationalPerson",
                "user"
        });

        context.setAttributeValue("cn", "s@%s.%s".formatted(user.firstName(), user.lastName()));
        context.setAttributeValue("description", DESCRIPTION);
        context.setAttributeValue("sAMAccountName", "%s.%s".formatted(user.firstName(), user.lastName())); // TODO: change
        context.setAttributeValue("displayName", fullName);
        context.setAttributeValue("givenName", user.firstName());
        context.setAttributeValue("mail", user.email());
        context.setAttributeValue("sn", user.lastName());
        context.setAttributeValue("telephoneNumber", user.phoneNumber());
        context.setAttributeValue("unicodePwd", encoderService.encodePassword(user.password())); // TODO: change
        context.setAttributeValue("userPrincipalName", "%s.%s@yigit.local".formatted(user.firstName(), user.lastName())); // TODO: change

        ldapTemplate.bind(context);

        unlockUser(dn);
    }


    public void resetPassword() {
        // TODO: implementation
    }


    public void lockUser() {
        // TODO: implementation
    }

    public void unlockUser(Name dn) {
        System.out.printf("\u001B[34mUNLOCKING USER: %s\u001B[0m%n", dn);

        ldapTemplate.modifyAttributes(dn, new ModificationItem[]{
                new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("userAccountControl", "512")) // TODO: needs edit
        });

        System.out.printf("\u001B[32mUSER UNLOCKED: %s\u001B[0m%n", dn);
    }
}
