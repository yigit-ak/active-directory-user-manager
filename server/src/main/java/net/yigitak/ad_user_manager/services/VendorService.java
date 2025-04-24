package net.yigitak.ad_user_manager.services;

import net.yigitak.ad_user_manager.dto.VendorDto;
import net.yigitak.ad_user_manager.exceptions.VendorFetchException;
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
        try {
            Name baseDn = LdapNameBuilder.newInstance().add("OU", PARENT_OU).build();
            String filter = "(&(objectClass=organizationalUnit)(objectCategory=organizationalUnit))";

            SearchControls searchControls = new SearchControls();
            searchControls.setSearchScope(SearchControls.ONELEVEL_SCOPE);

            return ldapTemplate.search(
                    baseDn,
                    filter,
                    searchControls,
                    (AttributesMapper<VendorDto>) attributes -> {
                        if (attributes.get("name") == null) {
                            throw new VendorFetchException("Missing 'name' attribute in LDAP OU");
                        }
                        return new VendorDto(attributes.get("name").get().toString());
                    }
            );
        } catch (Exception e) {
            throw new VendorFetchException("Failed to fetch vendors from LDAP", e);
        }
    }

}
