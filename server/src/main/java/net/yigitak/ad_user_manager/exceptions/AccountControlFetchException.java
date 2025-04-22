package net.yigitak.ad_user_manager.exceptions;

public class AccountControlFetchException extends RuntimeException {
    public AccountControlFetchException(String cn) {
        super("Could not fetch userAccountControl attribute for user: " + cn);
    }
}
