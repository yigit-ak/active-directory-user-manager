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

@Service
public class SendGridEmailService implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(SendGridEmailService.class);

    @Autowired
    private SendGrid sendGrid;

    @Value("${twilio.sendgrid.from-email}")
    private String fromEmail;

    private Content createEmailBody(String password) {
        return new Content("text/plain", "Your password is: " + password);
    }

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

    @Override
    public void sendAccountCreationMail(String toEmail, String password) {
        Email from = new Email(fromEmail);
        Email to = new Email(toEmail);
        Content body = createEmailBody(password);
        String subject = "Your Account is Ready!";
        Mail mail = new Mail(from, subject, to, body);
        sendEmail(mail);
    }

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
