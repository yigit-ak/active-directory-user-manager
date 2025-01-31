class UserCreateDto {
    constructor(firstName, lastName, email, phoneNumber, vendor) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.vendor = vendor;
    }

    toJson() {
        return JSON.stringify(this);
    }
}