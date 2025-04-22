package net.yigitak.ad_user_manager.util;

public class DnExtractor {
    public static String extractFirstOu(String dn) {
        for (String part : dn.split(",")) {
            if (part.startsWith("OU=")) {
                return part.substring(3); // Remove "OU=" prefix
            }
        }
        return null;
    }
}
