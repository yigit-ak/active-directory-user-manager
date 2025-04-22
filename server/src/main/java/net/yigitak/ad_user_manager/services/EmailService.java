package net.yigitak.ad_user_manager.services;

public interface EmailService {
    void sendAccountCreationMail(String toEmail, String password);
    void sendPasswordResetMail(String toEmail, String password);
}
