package net.yigitak.ad_user_manager.util;

import net.yigitak.ad_user_manager.enums.UserAccountControlFlag;

public class UserAccountControlUtil {

    /**
     * Enable user and set non-expiring password
     *
     * @return userAccountControl value
     */
    public static int getEnabledAccountWithExpiringPassword() {
        return UserAccountControlFlag.NORMAL_ACCOUNT.getValue() | UserAccountControlFlag.DONT_EXPIRE_PASSWD.getValue();
    }

    /**
     * Enable user (default interactive login)
     *
     * @return userAccountControl value
     */
    public static int getEnabledAccount() {
        return UserAccountControlFlag.NORMAL_ACCOUNT.getValue();
    }

    /**
     * Disable an existing user while keeping other flags intact
     *
     * @param userAccountControl The current userAccountControl value
     * @return New userAccountControl value with ACCOUNTDISABLE flag set
     */
    public static int disableAccount(int userAccountControl) {
        return userAccountControl | UserAccountControlFlag.ACCOUNTDISABLE.getValue();
    }

    public static int disableAccount(String userAccountControl) {
        return disableAccount(Integer.parseInt(userAccountControl));
    }

    /**
     * Enable a previously disabled user while keeping other flags intact
     *
     * @param userAccountControl The current userAccountControl value
     * @return New userAccountControl value with ACCOUNTDISABLE flag removed
     */
    public static int enableUser(int userAccountControl) {
        return userAccountControl & ~UserAccountControlFlag.ACCOUNTDISABLE.getValue();
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
        return (userAccountControl & UserAccountControlFlag.ACCOUNTDISABLE.getValue()) == 0;
    }

    public static boolean isAccountEnabled(String userAccountControl) {
        return isAccountEnabled(Integer.parseInt(userAccountControl));
    }
}
