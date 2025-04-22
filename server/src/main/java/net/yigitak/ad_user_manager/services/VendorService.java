package net.yigitak.ad_user_manager.services;

import net.yigitak.ad_user_manager.dto.VendorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import javax.naming.directory.SearchControls;
import java.util.List;

@Service
public class VendorService {

    @Value("${parent-organizational-unit}")
    private String PARENT_OU;

    @Autowired
    private LdapTemplate ldapTemplate;

    public List<VendorDto> getAll() {
        // Build the base DN
        Name baseDn = LdapNameBuilder.newInstance().add("OU", PARENT_OU).build();

        // LDAP filter for Organizational Units
        String filter = "(&(objectClass=organizationalUnit)(objectCategory=organizationalUnit))";

        // Create SearchControls with ONELEVEL_SCOPE
        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.ONELEVEL_SCOPE);

        // Perform the search
        return ldapTemplate.search(
                baseDn,
                filter,
                searchControls,
                (AttributesMapper<VendorDto>) attributes -> new VendorDto(
                        attributes.get("name").get().toString()
                )
        );
    }

}
