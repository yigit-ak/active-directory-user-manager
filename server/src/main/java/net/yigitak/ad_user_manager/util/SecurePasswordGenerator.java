package net.yigitak.ad_user_manager.util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Utility class for generating secure random passwords.
 * <p>
 * Passwords include uppercase, lowercase, digits, and special characters.
 */
public class SecurePasswordGenerator {
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_+=<>?";
    private static final String ALL_CHARACTERS = UPPERCASE + LOWERCASE + DIGITS + SPECIAL_CHARACTERS;

    private static final SecureRandom random = new SecureRandom();

    /**
     * Generates a random password with a default length of 10 characters.
     *
     * @return the generated password
     */
    public static String generatePassword() {
        return generatePassword(10);
    }

    /**
     * Generates a random password of specified length.
     * <p>
     * The password will contain at least one character from each category.
     *
     * @param length the desired password length
     * @return the generated password
     * @throws IllegalArgumentException if the length is less than 10
     */
    public static String generatePassword(int length) {
        if (length < 10) {
            throw new IllegalArgumentException("Password length must be at least 10 characters.");
        }

        List<Character> passwordChars = new ArrayList<>();

        // Add at least one character from each category
        passwordChars.add(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
        passwordChars.add(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
        passwordChars.add(DIGITS.charAt(random.nextInt(DIGITS.length())));
        passwordChars.add(SPECIAL_CHARACTERS.charAt(random.nextInt(SPECIAL_CHARACTERS.length())));

        // Fill the rest randomly
        for (int i = 4; i < length; i++) {
            passwordChars.add(ALL_CHARACTERS.charAt(random.nextInt(ALL_CHARACTERS.length())));
        }

        Collections.shuffle(passwordChars);

        StringBuilder password = new StringBuilder();
        for (char c : passwordChars) {
            password.append(c);
        }

        return password.toString();
    }
}
