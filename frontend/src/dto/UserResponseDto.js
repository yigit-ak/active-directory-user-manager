class UserResponseDto {
    constructor(vendor, commonName, samAccountName, displayName, firstName, lastName, email, phoneNumber, isEnabled) {
        this.vendor = vendor;
        this.commonName = commonName;
        this.samAccountName = samAccountName;
        this.displayName = displayName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.isEnabled = isEnabled;
    }

    static fromJson(json) {
        const data = JSON.parse(json);
        return new UserResponseDto(
            data.vendor,
            data.commonName,
            data.samAccountName,
            data.displayName,
            data.firstName,
            data.lastName,
            data.email,
            data.phoneNumber,
            data.isEnabled
        );
    }
}
