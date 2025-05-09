package net.yigitak.ad_user_manager.services;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import net.yigitak.ad_user_manager.exceptions.EmailSendException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Implementation of {@link EmailService} using SendGrid API.
 * <p>
 * Responsible for sending account creation and password reset emails to users.
 */
@Service
public class SendGridEmailService implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(SendGridEmailService.class);

    @Autowired
    private SendGrid sendGrid;

    @Value("${twilio.sendgrid.from-email}")
    private String fromEmail;

    /**
     * Creates the body content for the email, including the given password.
     *
     * @param password the password to include in the email body
     * @return the email content
     */
    private Content createEmailBody(String password) {
        return new Content("text/plain", "Your password is: " + password);
    }

    /**
     * Sends an email using the SendGrid API.
     *
     * @param mail the email to send
     * @throws EmailSendException if sending fails
     */
    private void sendEmail(Mail mail) {
        try {
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sendGrid.api(request);
            int statusCode = response.getStatusCode();

            if (statusCode < 200 || statusCode >= 300) {
                logger.error("SendGrid API returned non-success status: {} - {}", statusCode, response.getBody());
                throw new EmailSendException("Failed to send email: " + response.getBody());
            }

        } catch (IOException e) {
            logger.error("IOException when sending email via SendGrid", e);
            throw new EmailSendException("Failed to send email due to IO error", e);
        }
    }

    /**
     * Sends an account creation email to the specified user.
     *
     * @param toEmail  the recipient's email address
     * @param password the generated password to include in the email
     */
    @Override
    public void sendAccountCreationMail(String toEmail, String password) {
        Email from = new Email(fromEmail);
        Email to = new Email(toEmail);
        Content body = createEmailBody(password);
        String subject = "Your Account is Ready!";
        Mail mail = new Mail(from, subject, to, body);
        sendEmail(mail);
    }

    /**
     * Sends a password reset email to the specified user.
     *
     * @param toEmail  the recipient's email address
     * @param password the new password to include in the email
     */
    @Override
    public void sendPasswordResetMail(String toEmail, String password) {
        Email from = new Email(fromEmail);
        Email to = new Email(toEmail);
        Content body = createEmailBody(password);
        String subject = "Your Password Has Reset!";
        Mail mail = new Mail(from, subject, to, body);
        sendEmail(mail);
    }
}
