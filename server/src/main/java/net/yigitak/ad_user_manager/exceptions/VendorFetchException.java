package net.yigitak.ad_user_manager.exceptions;

public class VendorFetchException extends RuntimeException {
    public VendorFetchException(String message) {
        super(message);
    }

    public VendorFetchException(String message, Throwable cause) {
        super(message, cause);
    }
}
