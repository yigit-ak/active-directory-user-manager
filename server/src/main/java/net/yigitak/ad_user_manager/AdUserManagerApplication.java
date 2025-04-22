package net.yigitak.ad_user_manager;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AdUserManagerApplication {

    @Value("${trust-store.disable-endpoint-identification}")
    private String DISABLE_ENDPOINT_IDENTIFICATION;

    @Value("${trust-store.path}")
    private String TRUST_STORE;

    @Value("${trust-store.password}")
    private String TRUST_STORE_PASSWORD;

    @PostConstruct
    private void setupSystemProperties() {
        System.setProperty("com.sun.jndi.ldap.object.disableEndpointIdentification", DISABLE_ENDPOINT_IDENTIFICATION);
        System.setProperty("javax.net.ssl.trustStore", TRUST_STORE);
        System.setProperty("javax.net.ssl.trustStorePassword", TRUST_STORE_PASSWORD);
    }

    public static void main(String[] args) {
        SpringApplication.run(AdUserManagerApplication.class, args);
    }

}
