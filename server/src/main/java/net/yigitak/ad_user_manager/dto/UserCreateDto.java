package net.yigitak.ad_user_manager.dto;

public record UserCreateDto(
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String vendor
) {
    @Override
    public String toString() {
        return "UserDto{firstName='%s', lastName='%s', email='%s', phoneNumber='%s', vendor='%s'}"
                .formatted(firstName, lastName, email, phoneNumber, vendor);
    }
}
