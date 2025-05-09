package net.yigitak.ad_user_manager.util;

import java.nio.charset.StandardCharsets;

/**
 * Utility class for encoding passwords in a format compatible with Active Directory.
 */
public class PasswordEncoder {

    /**
     * Encodes a plain text password by enclosing it in quotes and converting it to a UTF-16LE byte array.
     *
     * @param password the plain text password to encode
     * @return the encoded password as a UTF-16LE byte array
     * @throws IllegalArgumentException if password is null
     */
    public static byte[] encodePassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }

        String quotedPassword = "\"%s\"".formatted(password);

        // Convert to UTF-16LE (Little Endian) byte array
        return quotedPassword.getBytes(StandardCharsets.UTF_16LE);
    }
}
