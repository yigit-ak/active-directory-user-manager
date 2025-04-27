package net.yigitak.ad_user_manager.services;

import net.yigitak.ad_user_manager.dto.UserCreateDto;
import net.yigitak.ad_user_manager.dto.UserResponseDto;
import net.yigitak.ad_user_manager.enums.ActionType;
import net.yigitak.ad_user_manager.exceptions.AccountControlFetchException;
import net.yigitak.ad_user_manager.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.LdapTemplate;

import javax.naming.Name;
import javax.naming.directory.ModificationItem;
import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class UserServiceTest {

    @Mock
    private LdapTemplate ldapTemplate;

    @Mock
    private EmailService emailService;

    @Mock
    private ActionLogService actionLogService;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup() throws Exception {
        setPrivateField("PARENT_ORGANIZATIONAL_UNIT", "Vendors");
        setPrivateField("LDAP_BASE", "dc=example,dc=com");
        setPrivateField("DESCRIPTION", "Test user description");
    }

    private void setPrivateField(String fieldName, String value) throws Exception {
        Field field = UserService.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(userService, value);
    }

    @Test
    void findUserByCn_shouldReturnUser() {
        // Arrange
        UserResponseDto user = mockUserResponse();
        when(ldapTemplate.search(any(), any(AttributesMapper.class)))
                .thenReturn(List.of(user));

        // Act
        UserResponseDto result = userService.findUserByCn("john.doe");

        // Assert
        assertNotNull(result);
        assertEquals(user.cn(), result.cn());
    }

    @Test
    void findUserByCn_shouldThrowException_whenUserNotFound() {
        when(ldapTemplate.search(any(), any(AttributesMapper.class)))
                .thenReturn(List.of());

        assertThrows(UserNotFoundException.class, () -> userService.findUserByCn("unknown"));
    }

    @Test
    void createNewUser_shouldCreateAndSendEmailAndLog() {
        // Arrange
        when(ldapTemplate.search(any(), any(AttributesMapper.class)))
                .thenReturn(List.of(mockUserResponse()))  // for unlockUser
                .thenReturn(List.of("512"));              // for unlockUser's userAccountControl

        // Act
        userService.createNewUser(new UserCreateDto("John", "Doe", "john@example.com", "1234567890", "IT"));

        // Assert
        verify(ldapTemplate).bind(any(DirContextAdapter.class));
        verify(emailService).sendAccountCreationMail(eq("john@example.com"), anyString());
        verify(actionLogService).logAction(eq(ActionType.CREATE_USER), anyString());
    }

    @Test
    void resetPassword_shouldResetAndSendEmailAndLog() {
        // Arrange
        when(ldapTemplate.search(any(), any(AttributesMapper.class)))
                .thenReturn(List.of(mockUserResponse()));

        // Act
        userService.resetPassword("john.doe");

        // Assert
        verify(ldapTemplate).modifyAttributes(any(Name.class), any(ModificationItem[].class));
        verify(emailService).sendPasswordResetMail(eq("john@example.com"), anyString());
        verify(actionLogService).logAction(eq(ActionType.RESET_PASSWORD), eq("john.doe"));
    }

    @Test
    void lockUser_shouldDisableAccountAndLog() {
        // Arrange
        when(ldapTemplate.search(any(), any(AttributesMapper.class)))
                .thenReturn(List.of(mockUserResponse())) // for findUserByCn
                .thenReturn(List.of("512"));              // userAccountControl value (enabled)

        // Act
        userService.lockUser("john.doe");

        // Assert
        verify(ldapTemplate).modifyAttributes(any(Name.class), any(ModificationItem[].class));
        verify(actionLogService).logAction(eq(ActionType.LOCK_USER), eq("john.doe"));
    }

    @Test
    void unlockUser_shouldEnableAccountAndLog() {
        // Arrange
        when(ldapTemplate.search(any(), any(AttributesMapper.class)))
                .thenReturn(List.of(mockUserResponse())) // for findUserByCn
                .thenReturn(List.of("514"));              // userAccountControl value (disabled)

        // Act
        userService.unlockUser("john.doe");

        // Assert
        verify(ldapTemplate).modifyAttributes(any(Name.class), any(ModificationItem[].class));
        verify(actionLogService).logAction(eq(ActionType.UNLOCK_USER), eq("john.doe"));
    }

    @Test
    void lockUser_shouldThrowAccountControlFetchException_whenControlValueMissing() {
        when(ldapTemplate.search(any(), any(AttributesMapper.class)))
                .thenReturn(List.of(mockUserResponse()))  // user found
                .thenReturn(List.of());                   // empty control attributes list

        assertThrows(AccountControlFetchException.class, () -> userService.lockUser("john.doe"));
    }

    private UserResponseDto mockUserResponse() {
        return new UserResponseDto(
                "john.doe",
                "Vendors",
                "john.doe",
                "jdoe",
                "John Doe",
                "John",
                "Doe",
                "john@example.com",
                "1234567890",
                true
        );
    }
}
