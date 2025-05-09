package net.yigitak.ad_user_manager.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleUserNotFound_shouldReturnNotFoundResponse() {
        UserNotFoundException ex = new UserNotFoundException("john.doe");

        ResponseEntity<String> response = handler.handleUserNotFound(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User not found: john.doe", response.getBody());
    }

    @Test
    void handleAccountControlFetchFailure_shouldReturnInternalServerError() {
        AccountControlFetchException ex = new AccountControlFetchException("john.doe");

        ResponseEntity<String> response = handler.handleUacFetchFailure(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Could not fetch userAccountControl attribute for user: john.doe", response.getBody());
    }

    @Test
    void handleEmailSendException_shouldReturnInternalServerError() {
        EmailSendException ex = new EmailSendException("Failed to send email");

        ResponseEntity<String> response = handler.handleEmailSendException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to send email: Failed to send email", response.getBody());
    }

    @Test
    void handleVendorFetchException_shouldReturnInternalServerError() {
        VendorFetchException ex = new VendorFetchException("Vendor fetch failed");

        ResponseEntity<String> response = handler.handleVendorFetch(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to retrieve vendor list: Vendor fetch failed", response.getBody());
    }

    @Test
    void handleGenericException_shouldReturnInternalServerError() {
        Exception ex = new RuntimeException("Some unexpected error");

        ResponseEntity<String> response = handler.handleGenericException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("Internal server error: Some unexpected error"));
    }
}
