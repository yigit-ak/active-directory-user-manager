package net.yigitak.ad_user_manager.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.security.KeyStore;

@Configuration
public class LdapConfig {

    private static final Logger logger = LoggerFactory.getLogger(LdapConfig.class);

    @Value("${ldap.url}")
    private String LDAP_URL;

    @Value("${ldap.base}")
    private String LDAP_BASE;

    @Value("${ldap.userDn}")
    private String LDAP_USER_DN;

    @Value("${ldap.password}")
    private String LDAP_PASSWORD;

    @Value("${ldap.trust-store}")
    private String TRUST_STORE;

    @Value("${ldap.trust-store-password}")
    private String TRUST_STORE_PASSWORD;

    @Bean
    public LdapContextSource contextSource() {
        logger.info("CONFIGURING LDAPS CONNECTION...");

        try {
            // ✅ Step 1: Load TrustStore for SSL/TLS
            logger.info("Loading TrustStore from: {}", TRUST_STORE);
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());

            try (FileInputStream trustStoreStream = new FileInputStream(getClass().getClassLoader().getResource(TRUST_STORE).getFile())) {
                trustStore.load(trustStoreStream, TRUST_STORE_PASSWORD.toCharArray());
                logger.info("TrustStore loaded successfully.");
            } catch (Exception e) {
                logger.error("Failed to load TrustStore: {}", e.getMessage());
                throw new RuntimeException("TrustStore loading failed", e);
            }

            // ✅ Step 2: Initialize TrustManagerFactory
            logger.info("Initializing TrustManagerFactory...");
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(trustStore);
            logger.info("TrustManagerFactory initialized successfully.");

            // ✅ Step 3: Initialize SSLContext
            logger.info("Initializing SSLContext...");
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
            logger.info("SSLContext initialized successfully.");

            // ✅ Step 4: Configure LDAPS Connection
            logger.info("Configuring LDAP connection to {}", LDAP_URL);
            LdapContextSource contextSource = new LdapContextSource();
            contextSource.setUrl(LDAP_URL);
            contextSource.setBase(LDAP_BASE);
            contextSource.setUserDn(LDAP_USER_DN);
            contextSource.setPassword(LDAP_PASSWORD);
            contextSource.setPooled(true);
            contextSource.afterPropertiesSet(); // Ensures the bean is initialized properly

            logger.info("LDAPS CONNECTION CONFIGURED SUCCESSFULLY!");
            return contextSource;
        } catch (Exception e) {
            logger.error("Error configuring LDAP connection: {}", e.getMessage(), e);
            throw new RuntimeException("LDAP configuration failed", e);
        }
    }

    @Bean
    public LdapTemplate ldapTemplate() {
        logger.info("Creating LDAP Template...");
        try {
            LdapTemplate ldapTemplate = new LdapTemplate(contextSource());
            logger.info("LDAP Template created successfully.");
            return ldapTemplate;
        } catch (Exception e) {
            logger.error("Failed to create LDAP Template: {}", e.getMessage(), e);
            throw new RuntimeException("LDAP Template creation failed", e);
        }
    }

}
