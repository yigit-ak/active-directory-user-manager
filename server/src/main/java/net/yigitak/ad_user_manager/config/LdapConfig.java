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
        System.out.println("\u001B[34m" +
                "CONTEXT IS GETTING SET" +
                "\u001B[0m");
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl(LDAP_URL);
        contextSource.setBase(LDAP_BASE);
        contextSource.setUserDn(LDAP_USER_DN);
        contextSource.setPassword(LDAP_PASSWORD);
        contextSource.setPooled(true);
        System.out.println("\u001B[34m" +
                "CONTEXT SET" +
                "\u001B[0m");
        return contextSource;
    }

    @Bean
    public LdapTemplate ldapTemplate() {
        System.out.println("\u001B[34mGETTING LDAP TEMPLATE\u001B[0m");
        return new LdapTemplate(contextSource());
    }

}
