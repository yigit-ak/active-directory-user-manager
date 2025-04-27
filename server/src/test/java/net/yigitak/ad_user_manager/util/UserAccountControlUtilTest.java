package net.yigitak.ad_user_manager.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserAccountControlUtilTest {

    @Test
    void getEnabledAccountWithExpiringPassword_shouldReturnCorrectFlags() {
        int expected = 0x200 | 0x10000; // NORMAL_ACCOUNT | DONT_EXPIRE_PASSWD
        int result = UserAccountControlUtil.getEnabledAccountWithExpiringPassword();
        assertEquals(expected, result);
    }

    @Test
    void getEnabledAccount_shouldReturnNormalAccountFlag() {
        int expected = 0x200; // NORMAL_ACCOUNT
        int result = UserAccountControlUtil.getEnabledAccount();
        assertEquals(expected, result);
    }

    @Test
    void disableAccount_shouldSetAccountDisableFlag_int() {
        int normalAccount = UserAccountControlUtil.getEnabledAccount();
        int disabledAccount = UserAccountControlUtil.disableAccount(normalAccount);

        assertFalse(UserAccountControlUtil.isAccountEnabled(disabledAccount));
    }

    @Test
    void disableAccount_shouldSetAccountDisableFlag_string() {
        int normalAccount = UserAccountControlUtil.getEnabledAccount();
        int disabledAccount = UserAccountControlUtil.disableAccount(Integer.toString(normalAccount));

        assertFalse(UserAccountControlUtil.isAccountEnabled(disabledAccount));
    }

    @Test
    void enableUser_shouldClearAccountDisableFlag_int() {
        int normalAccount = UserAccountControlUtil.getEnabledAccount();
        int disabledAccount = UserAccountControlUtil.disableAccount(normalAccount);

        int reEnabledAccount = UserAccountControlUtil.enableUser(disabledAccount);

        assertTrue(UserAccountControlUtil.isAccountEnabled(reEnabledAccount));
    }

    @Test
    void enableUser_shouldClearAccountDisableFlag_string() {
        int normalAccount = UserAccountControlUtil.getEnabledAccount();
        int disabledAccount = UserAccountControlUtil.disableAccount(normalAccount);

        int reEnabledAccount = UserAccountControlUtil.enableUser(Integer.toString(disabledAccount));

        assertTrue(UserAccountControlUtil.isAccountEnabled(reEnabledAccount));
    }

    @Test
    void isAccountEnabled_shouldReturnTrueForEnabledAccount_int() {
        int normalAccount = UserAccountControlUtil.getEnabledAccount();
        assertTrue(UserAccountControlUtil.isAccountEnabled(normalAccount));
    }

    @Test
    void isAccountEnabled_shouldReturnFalseForDisabledAccount_int() {
        int normalAccount = UserAccountControlUtil.getEnabledAccount();
        int disabledAccount = UserAccountControlUtil.disableAccount(normalAccount);

        assertFalse(UserAccountControlUtil.isAccountEnabled(disabledAccount));
    }

    @Test
    void isAccountEnabled_shouldReturnTrueForEnabledAccount_string() {
        int normalAccount = UserAccountControlUtil.getEnabledAccount();
        assertTrue(UserAccountControlUtil.isAccountEnabled(Integer.toString(normalAccount)));
    }

    @Test
    void isAccountEnabled_shouldReturnFalseForDisabledAccount_string() {
        int normalAccount = UserAccountControlUtil.getEnabledAccount();
        int disabledAccount = UserAccountControlUtil.disableAccount(normalAccount);

        assertFalse(UserAccountControlUtil.isAccountEnabled(Integer.toString(disabledAccount)));
    }
}
