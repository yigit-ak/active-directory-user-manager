package net.yigitak.ad_user_manager.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DnExtractorTest {

    @Test
    void extractFirstOu_shouldReturnFirstOu_whenDnHasMultipleOus() {
        String dn = "CN=John Doe,OU=Sales,OU=Users,DC=example,DC=com";
        String result = DnExtractor.extractFirstOu(dn);
        assertEquals("Sales", result);
    }

    @Test
    void extractFirstOu_shouldReturnNull_whenDnHasNoOu() {
        String dn = "CN=John Doe,DC=example,DC=com";
        String result = DnExtractor.extractFirstOu(dn);
        assertNull(result);
    }

    @Test
    void extractFirstOu_shouldReturnFirstOu_whenDnHasOneOu() {
        String dn = "CN=Jane Doe,OU=Marketing,DC=example,DC=com";
        String result = DnExtractor.extractFirstOu(dn);
        assertEquals("Marketing", result);
    }

    @Test
    void extractFirstOu_shouldReturnNull_whenDnIsEmpty() {
        String dn = "";
        String result = DnExtractor.extractFirstOu(dn);
        assertNull(result);
    }

    @Test
    void extractFirstOu_shouldReturnNull_whenDnIsNull() {
        String result = DnExtractor.extractFirstOu(null);
        assertNull(result);
    }

    @Test
    void extractFirstOu_shouldHandleWhitespace() {
        String dn = " CN=John Doe , OU=Finance ,DC=example,DC=com ";
        String result = DnExtractor.extractFirstOu(dn.replaceAll("\\s+", ""));
        assertEquals("Finance", result);
    }
}
