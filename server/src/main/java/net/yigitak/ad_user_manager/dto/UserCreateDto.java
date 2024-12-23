package net.yigitak.ad_user_manager.dto;

public record UserDto(
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String vendor
) {
    @Override
    public String toString() {
        return "UserDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", vendor='" + vendor + '\'' +
                '}';
    }
}
