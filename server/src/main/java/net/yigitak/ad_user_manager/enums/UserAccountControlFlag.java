package net.yigitak.ad_user_manager.enums;

/**
 * Enum representing different userAccountControl flags in Active Directory.
 */
public enum UserAccountControlFlag {
    NORMAL_ACCOUNT(0x200),
    DONT_EXPIRE_PASSWD(0x10000),
    ACCOUNTDISABLE(0x2);

    private final int value;

    UserAccountControlFlag(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
