package net.yigitak.ad_user_manager.services;

/**
 * Service interface for sending account-related emails to users.
 * <p>
 * Provides methods for sending account creation and password reset emails.
 */
public interface EmailService {

    /**
     * Sends an account creation email to the specified user.
     *
     * @param toEmail  the recipient's email address
     * @param password the generated password to include in the email
     */
    void sendAccountCreationMail(String toEmail, String password);

    /**
     * Sends a password reset email to the specified user.
     *
     * @param toEmail  the recipient's email address
     * @param password the new password to include in the email
     */
    void sendPasswordResetMail(String toEmail, String password);
}
