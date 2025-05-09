package net.yigitak.ad_user_manager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserCreateDto(
        @NotBlank(message = "First name must not be blank")
        @Size(max = 50, message = "First name must be at most 50 characters")
        String firstName,

        @NotBlank(message = "Last name must not be blank")
        @Size(max = 50, message = "Last name must be at most 50 characters")
        String lastName,

        @Email(message = "Email should be valid")
        @NotBlank(message = "Email must not be blank")
        String email,

        @Pattern(regexp = "^[0-9+\\-()\\s]{7,20}$", message = "Phone number must be valid")
        String phoneNumber,

        @NotBlank(message = "Vendor must not be blank")
        String vendor
) {
}
