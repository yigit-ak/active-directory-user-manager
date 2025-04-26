package net.yigitak.ad_user_manager.util;

/**
 * Utility class for extracting information from Distinguished Names (DNs) in LDAP.
 */
public class DnExtractor {

    /**
     * Extracts the first Organizational Unit (OU) from a given Distinguished Name (DN).
     * <p>
     * For example, given "CN=John Doe,OU=Sales,OU=Users,DC=example,DC=com",
     * this method would return "Sales".
     *
     * @param dn the full distinguished name string
     * @return the first organizational unit (OU) found, or {@code null} if none is found
     */
    public static String extractFirstOu(String dn) {
        for (String part : dn.split(",")) {
            if (part.startsWith("OU=")) {
                return part.substring(3); // Remove "OU=" prefix
            }
        }
        return null;
    }
}
