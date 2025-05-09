package net.yigitak.ad_user_manager.util;

import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import static org.junit.jupiter.api.Assertions.*;

class PasswordEncoderTest {

    @Test
    void encodePassword_shouldReturnQuotedPasswordInUtf16Le() {
        String password = "MySecurePassword123";
        byte[] encoded = PasswordEncoder.encodePassword(password);

        String decoded = new String(encoded, StandardCharsets.UTF_16LE);

        assertEquals("\"MySecurePassword123\"", decoded);
    }

    @Test
    void encodePassword_shouldHandleEmptyPassword() {
        String password = "";
        byte[] encoded = PasswordEncoder.encodePassword(password);

        String decoded = new String(encoded, StandardCharsets.UTF_16LE);

        assertEquals("\"\"", decoded); // An empty password still should be enclosed in quotes
    }

    @Test
    void encodePassword_shouldHandleSpecialCharacters() {
        String password = "P@ssw0rd!#%";
        byte[] encoded = PasswordEncoder.encodePassword(password);

        String decoded = new String(encoded, StandardCharsets.UTF_16LE);

        assertEquals("\"P@ssw0rd!#%\"", decoded);
    }

    @Test
    void encodePassword_shouldHandleUnicodeCharacters() {
        String password = "Pass123!";
        byte[] encoded = PasswordEncoder.encodePassword(password);

        String decoded = new String(encoded, StandardCharsets.UTF_16LE);

        assertEquals("\"Pass123!\"", decoded);
    }

    @Test
    void encodePassword_shouldHandleNullPassword() {
        assertThrows(IllegalArgumentException.class, () -> PasswordEncoder.encodePassword(null));
    }
}
