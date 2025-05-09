package net.yigitak.ad_user_manager.services;

import net.yigitak.ad_user_manager.entities.ActionLog;
import net.yigitak.ad_user_manager.enums.ActionType;
import net.yigitak.ad_user_manager.repositories.ActionLogRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.core.context.SecurityContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ActionLogServiceTest {

    @Mock
    private ActionLogRepository actionLogRepository;

    @InjectMocks
    private ActionLogService actionLogService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Clear SecurityContext before each test to avoid leaks
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void tearDown() {
        // Clean SecurityContext after each test
        SecurityContextHolder.clearContext();
    }

    @Test
    void logAction_shouldSaveActionLog_withCurrentAuthenticatedUser() {
        // Arrange
        String email = "john@example.com";
        mockAuthenticatedUser(email);

        // Act
        actionLogService.logAction(ActionType.RESET_PASSWORD, "john.doe");

        // Assert
        ArgumentCaptor<ActionLog> captor = ArgumentCaptor.forClass(ActionLog.class);
        verify(actionLogRepository).save(captor.capture());

        ActionLog savedLog = captor.getValue();
        assertEquals(email, savedLog.getPerformedBy());
        assertEquals(ActionType.RESET_PASSWORD, savedLog.getActionType());
        assertEquals("john.doe", savedLog.getTargetUserCn());
    }

    @Test
    void getCurrentUsername_shouldReturnEmail_whenAuthenticatedOidcUser() {
        // Arrange
        String email = "jane@example.com";
        mockAuthenticatedUser(email);

        // Act
        String result = actionLogService.getCurrentUsername();

        // Assert
        assertEquals(email, result);
    }

    @Test
    void getCurrentUsername_shouldReturnUnknown_whenNoAuthentication() {
        // Arrange
        SecurityContextHolder.clearContext(); // No authentication set

        // Act
        String result = actionLogService.getCurrentUsername();

        // Assert
        assertEquals("UNKNOWN_USER", result);
    }

    @Test
    void getCurrentUsername_shouldReturnUnknown_whenAuthenticationIsNotOidcUser() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn("plain-string-user");

        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(context);

        // Act
        String result = actionLogService.getCurrentUsername();

        // Assert
        assertEquals("UNKNOWN_USER", result);
    }

    private void mockAuthenticatedUser(String email) {
        OidcUser oidcUser = mock(OidcUser.class);
        when(oidcUser.getEmail()).thenReturn(email);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(oidcUser);

        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(context);
    }
}
