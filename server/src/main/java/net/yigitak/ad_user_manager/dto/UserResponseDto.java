package net.yigitak.ad_user_manager.dto;

public record UserResponseDto(
        String cn,
        String vendor,
        String commonName,
        String samAccountName,
        String displayName,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        boolean isEnabled
) {
}
