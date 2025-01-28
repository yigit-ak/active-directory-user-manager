package net.yigitak.ad_user_manager.util;

public class UserAccountControlUtil {
    // userAccountControl flags
    private static final int NORMAL_ACCOUNT = 0x200; // 512
    private static final int DONT_EXPIRE_PASSWD = 0x10000; // 65536
    private static final int ACCOUNTDISABLE = 0x2; // 2

    /**
     * Enable user and set non-expiring password
     *
     * @return userAccountControl value
     */
    public static int getEnabledAccountWithExpiringPassword() {
        return NORMAL_ACCOUNT | DONT_EXPIRE_PASSWD;
    }

    /**
     * Enable user (default interactive login)
     *
     * @return userAccountControl value
     */
    public static int getEnabledAccount() {
        return NORMAL_ACCOUNT;
    }

    /**
     * Disable an existing user while keeping other flags intact
     * @param userAccountControl The current userAccountControl value
     * @return New userAccountControl value with ACCOUNTDISABLE flag set
     */
    public static int disableAccount(int userAccountControl) {
        return userAccountControl | ACCOUNTDISABLE;
    }

    public static int disableAccount(String userAccountControl) {
        return disableAccount(Integer.parseInt(userAccountControl));
    }

    /**
     * Enable a previously disabled user while keeping other flags intact
     * @param userAccountControl The current userAccountControl value
     * @return New userAccountControl value with ACCOUNTDISABLE flag removed
     */
    public static int enableUser(int userAccountControl) {
        return userAccountControl & ~ACCOUNTDISABLE; // Removes the disabled flag
    }

    public static int enableUser(String userAccountControl) {
        return enableUser(Integer.parseInt(userAccountControl));
    }

    /**
     * Check if an account is enabled
     *
     * @param userAccountControl current value
     * @return true if enabled, false otherwise
     */
    public static boolean isAccountEnabled(int userAccountControl) {
        return (userAccountControl & ACCOUNTDISABLE) == 0;
    }

    public static boolean isAccountEnabled(String userAccountControl) {
        return isAccountEnabled(Integer.parseInt(userAccountControl));
    }

}
