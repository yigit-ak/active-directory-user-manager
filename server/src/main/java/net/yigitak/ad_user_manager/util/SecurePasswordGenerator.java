package net.yigitak.ad_user_manager.util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SecurePasswordGenerator {
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_+=<>?";
    private static final String ALL_CHARACTERS = UPPERCASE + LOWERCASE + DIGITS + SPECIAL_CHARACTERS;

    private static final SecureRandom random = new SecureRandom();

    public static String generatePassword() {
        return generatePassword(10);
    }

    public static String generatePassword(int length) {
        if (length < 10) {
            throw new IllegalArgumentException("Password length must be at least 10 characters.");
        }

        List<Character> passwordChars = new ArrayList<>();

        // at least one character from each category
        passwordChars.add(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
        passwordChars.add(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
        passwordChars.add(DIGITS.charAt(random.nextInt(DIGITS.length())));
        passwordChars.add(SPECIAL_CHARACTERS.charAt(random.nextInt(SPECIAL_CHARACTERS.length())));

        // fill the remaining characters randomly
        for (int i = 4; i < length; i++)
            passwordChars.add(ALL_CHARACTERS.charAt(random.nextInt(ALL_CHARACTERS.length())));

        Collections.shuffle(passwordChars);

        StringBuilder password = new StringBuilder();
        for (char c : passwordChars)
            password.append(c);

        return password.toString();
    }
}
