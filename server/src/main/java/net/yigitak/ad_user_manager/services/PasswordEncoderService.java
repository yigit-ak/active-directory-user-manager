package net.yigitak.ad_user_manager.services;

import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;

@Component
public class PasswordEncoderService {

    public byte[] encodePassword(String password) {
        String quotedPassword = "\"%s\"".formatted(password);  // AD requires quotes around passwords
        return quotedPassword.getBytes(StandardCharsets.UTF_16LE); // Convert to UTF-16LE
    }

}
