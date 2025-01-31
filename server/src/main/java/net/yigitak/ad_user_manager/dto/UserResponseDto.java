package net.yigitak.ad_user_manager.dto;

public record UserResponseDto(
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
    @Override
    public String toString() {
        return "UserResponseDto\n\tvendor='%s', \n\tcommonName='%s', \n\tsamAccountName='%s', \n\tdisplayName='%s', \n\tfirstName='%s', \n\tlastName='%s', \n\temail='%s', \n\tphoneNumber='%s', \n\tisEnabled=%s}".formatted(vendor, commonName, samAccountName, displayName, firstName, lastName, email, phoneNumber, isEnabled);
    }
}
