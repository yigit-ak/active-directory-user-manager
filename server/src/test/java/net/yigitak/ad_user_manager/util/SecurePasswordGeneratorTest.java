package net.yigitak.ad_user_manager.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SecurePasswordGeneratorTest {

    @Test
    void generatePassword_defaultLength_shouldReturnPasswordOfLength10() {
        String password = SecurePasswordGenerator.generatePassword();
        assertNotNull(password);
        assertEquals(10, password.length());
    }

    @Test
    void generatePassword_customLength_shouldReturnPasswordOfCorrectLength() {
        int customLength = 15;
        String password = SecurePasswordGenerator.generatePassword(customLength);
        assertNotNull(password);
        assertEquals(customLength, password.length());
    }

    @Test
    void generatePassword_shouldContainAtLeastOneUppercase() {
        String password = SecurePasswordGenerator.generatePassword(15);
        assertTrue(password.chars().anyMatch(Character::isUpperCase));
    }

    @Test
    void generatePassword_shouldContainAtLeastOneLowercase() {
        String password = SecurePasswordGenerator.generatePassword(15);
        assertTrue(password.chars().anyMatch(Character::isLowerCase));
    }

    @Test
    void generatePassword_shouldContainAtLeastOneDigit() {
        String password = SecurePasswordGenerator.generatePassword(15);
        assertTrue(password.chars().anyMatch(Character::isDigit));
    }

    @Test
    void generatePassword_shouldContainAtLeastOneSpecialCharacter() {
        String password = SecurePasswordGenerator.generatePassword(15);

        String specialChars = "!@#$%^&*()-_+=<>?";
        assertTrue(password.chars().mapToObj(c -> (char) c)
                .anyMatch(ch -> specialChars.indexOf(ch) >= 0));
    }

    @Test
    void generatePassword_shouldThrowException_whenLengthLessThan10() {
        assertThrows(IllegalArgumentException.class, () -> SecurePasswordGenerator.generatePassword(9));
    }
}
