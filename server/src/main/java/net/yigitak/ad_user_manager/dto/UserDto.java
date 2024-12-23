package net.yigitak.ad_user_manager.dto;

public record UserDto(
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String vendor
) {
}
