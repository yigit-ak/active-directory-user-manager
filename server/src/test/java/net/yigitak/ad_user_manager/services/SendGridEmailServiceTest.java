package net.yigitak.ad_user_manager.services;

import com.sendgrid.*;
import net.yigitak.ad_user_manager.exceptions.EmailSendException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SendGridEmailServiceTest {

    @Mock
    private SendGrid sendGrid;

    @InjectMocks
    private SendGridEmailService emailService;

    @BeforeEach
    void setup() throws Exception {
        // manually inject fromEmail (because @Value is not injected by Mockito)
        var field = SendGridEmailService.class.getDeclaredField("fromEmail");
        field.setAccessible(true);
        field.set(emailService, "noreply@example.com");
    }

    @Test
    void sendAccountCreationMail_shouldCallSendGridApiSuccessfully() throws IOException {
        // Arrange
        Response mockResponse = new Response(202, "Accepted", null); // success response
        when(sendGrid.api(any(Request.class))).thenReturn(mockResponse);

        // Act
        emailService.sendAccountCreationMail("user@example.com", "TestPassword123");

        // Assert
        verify(sendGrid).api(any(Request.class));
    }

    @Test
    void sendPasswordResetMail_shouldCallSendGridApiSuccessfully() throws IOException {
        // Arrange
        Response mockResponse = new Response(202, "Accepted", null); // success response
        when(sendGrid.api(any(Request.class))).thenReturn(mockResponse);

        // Act
        emailService.sendPasswordResetMail("user@example.com", "NewPassword456");

        // Assert
        verify(sendGrid).api(any(Request.class));
    }

    @Test
    void sendAccountCreationMail_shouldThrowEmailSendException_whenSendGridFails() throws IOException {
        // Arrange
        Response mockResponse = new Response(400, "Bad Request", null); // failure
        when(sendGrid.api(any(Request.class))).thenReturn(mockResponse);

        // Act + Assert
        EmailSendException exception = assertThrows(EmailSendException.class,
                () -> emailService.sendAccountCreationMail("user@example.com", "TestPassword123"));

        assertTrue(exception.getMessage().contains("Failed to send email"));
    }

    @Test
    void sendPasswordResetMail_shouldThrowEmailSendException_whenSendGridFails() throws IOException {
        // Arrange
        Response mockResponse = new Response(500, "Internal Server Error", null); // failure
        when(sendGrid.api(any(Request.class))).thenReturn(mockResponse);

        // Act + Assert
        EmailSendException exception = assertThrows(EmailSendException.class,
                () -> emailService.sendPasswordResetMail("user@example.com", "NewPassword456"));

        assertTrue(exception.getMessage().contains("Failed to send email"));
    }

    @Test
    void sendAccountCreationMail_shouldThrowEmailSendException_whenIOExceptionOccurs() throws IOException {
        // Arrange
        when(sendGrid.api(any(Request.class))).thenThrow(new IOException("Simulated IO error"));

        // Act + Assert
        EmailSendException exception = assertThrows(EmailSendException.class,
                () -> emailService.sendAccountCreationMail("user@example.com", "TestPassword123"));

        assertTrue(exception.getMessage().contains("Failed to send email due to IO error"));
    }
}
