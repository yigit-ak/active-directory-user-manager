package net.yigitak.ad_user_manager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
public class LdapConfig {

    @Value("${ldap.url}")
    private String LDAP_URL;

    @Value("${ldap.base}")
    private String LDAP_BASE;

    @Value("${ldap.userDn}")
    private String LDAP_USER_DN;

    @Value("${ldap.password}")
    private String LDAP_PASSWORD;

    @Bean
    public LdapContextSource contextSource() {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl(LDAP_URL);
        contextSource.setBase(LDAP_BASE);
        contextSource.setUserDn(LDAP_USER_DN);
        contextSource.setPassword(LDAP_PASSWORD);
        contextSource.afterPropertiesSet(); // Ensures the bean is initialized properly

        return contextSource;
    }

    @Bean
    public LdapTemplate ldapTemplate() {
        try {
            return new LdapTemplate(contextSource());
        } catch (Exception e) {
            throw new RuntimeException("LDAP Template creation failed", e);
        }
    }

}
