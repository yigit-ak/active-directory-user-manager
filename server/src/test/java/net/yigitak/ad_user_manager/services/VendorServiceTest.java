package net.yigitak.ad_user_manager.services;

import net.yigitak.ad_user_manager.dto.VendorDto;
import net.yigitak.ad_user_manager.exceptions.VendorFetchException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;

import javax.naming.Name;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.SearchControls;
import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VendorServiceTest {

    @Mock
    private LdapTemplate ldapTemplate;

    @InjectMocks
    private VendorService vendorService;

    @BeforeEach
    void setup() throws Exception {
        Field field = VendorService.class.getDeclaredField("PARENT_OU");
        field.setAccessible(true);
        field.set(vendorService, "Vendors");
    }

    @Test
    void getAll_shouldReturnVendorList_whenLdapReturnsValidData() {
        // Arrange
        Attributes mockAttributes = new BasicAttributes();
        mockAttributes.put("name", "VendorA");

        when(ldapTemplate.search(
                any(Name.class),
                anyString(),
                any(SearchControls.class),
                any(AttributesMapper.class)
        )).thenReturn(List.of(new VendorDto("VendorA")));

        // Act
        List<VendorDto> vendors = vendorService.getAll();

        // Assert
        assertNotNull(vendors);
        assertEquals(1, vendors.size());
        assertEquals("VendorA", vendors.getFirst().name());

        // Verify LDAP search was called properly
        ArgumentCaptor<Name> baseDnCaptor = ArgumentCaptor.forClass(Name.class);
        verify(ldapTemplate).search(
                baseDnCaptor.capture(),
                eq("(&(objectClass=organizationalUnit)(objectCategory=organizationalUnit))"),
                any(SearchControls.class),
                any(AttributesMapper.class)
        );

        Name capturedBaseDn = baseDnCaptor.getValue();
        assertEquals(LdapNameBuilder.newInstance().add("OU", "Vendors").build(), capturedBaseDn);
    }

    @Test
    void getAll_shouldThrowVendorFetchException_whenLdapThrowsException() {
        // Arrange
        when(ldapTemplate.search(
                any(Name.class),
                anyString(),
                any(SearchControls.class),
                any(AttributesMapper.class)
        )).thenThrow(new RuntimeException("Simulated LDAP failure"));

        // Act + Assert
        VendorFetchException exception = assertThrows(VendorFetchException.class, () -> vendorService.getAll());
        assertTrue(exception.getMessage().contains("Failed to fetch vendors"));
    }
}
