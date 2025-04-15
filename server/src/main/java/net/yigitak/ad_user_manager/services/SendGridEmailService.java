package net.yigitak.ad_user_manager.services;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SendGridEmailService implements EmailService{

    @Autowired
    private SendGrid sendGrid;

    @Value("${twilio.sendgrid.from-email}")
    private String fromEmail;

    private Content createEmailBody (String password) {
        return new Content("text/plain", "Your new password is: " + password);
    }

    private void sendEmail(Mail mail) {
        try {
            // set the SendGrid API endpoint details as described
            // in the doc (https://docs.sendgrid.com/api-reference/mail-send/mail-send)
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            // perform the request and send the email
            Response response = sendGrid.api(request);
            int statusCode = response.getStatusCode();
            // if the status code is not 2xx
            if (statusCode < 200 || statusCode >= 300) {
                throw new RuntimeException(response.getBody());
            }
        } catch (IOException e) {
            // log the error message in case of network failures
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void sendPassword(String toEmail, String password) {
        Email from = new Email(fromEmail);
        Email to = new Email(toEmail);

        Content body = createEmailBody(password);

        String subject = "Your Account is Ready!";
        Mail mail = new Mail(from, subject, to, body);

        sendEmail(mail);
    }

}
