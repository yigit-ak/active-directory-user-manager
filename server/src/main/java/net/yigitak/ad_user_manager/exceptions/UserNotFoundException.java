package net.yigitak.ad_user_manager.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String cn) {
        super("User not found: " + cn);
    }
}
