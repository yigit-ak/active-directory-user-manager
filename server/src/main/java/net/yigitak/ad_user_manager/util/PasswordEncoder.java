package net.yigitak.ad_user_manager.util;

import java.nio.charset.StandardCharsets;

/**
 * Utility class for encoding passwords in a format compatible with Active Directory.
 * <p>
 * Passwords must be enclosed in quotes and encoded using UTF-16LE (Little Endian).
 */
public class PasswordEncoder {

    /**
     * Encodes a plain text password by enclosing it in quotes and converting it to a UTF-16LE byte array.
     * <p>
     * This encoding is required when setting the {@code unicodePwd} attribute in Active Directory.
     *
     * @param password the plain text password to encode
     * @return the encoded password as a UTF-16LE byte array
     */
    public static byte[] encodePassword(String password) {
        // must be enclosed in quotes
        String quotedPassword = "\"%s\"".formatted(password);

        // Convert to UTF-16LE (Little Endian) byte array
        return quotedPassword.getBytes(StandardCharsets.UTF_16LE);
    }
}
