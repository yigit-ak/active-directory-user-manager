package net.yigitak.ad_user_manager.util;

import java.nio.charset.StandardCharsets;

public class PasswordEncoder {
    public static byte[] encodePassword(String password) {
        // must be enclosed in quotes
        String quotedPassword = "\"%s\"".formatted(password);

        // Convert to UTF-16LE (Little Endian) byte array
        return quotedPassword.getBytes(StandardCharsets.UTF_16LE);
    }

}
