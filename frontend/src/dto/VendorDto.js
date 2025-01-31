class VendorResponseDto {
    constructor(name) {
        this.name = name;
    }

    static fromJson(json) {
        const data = JSON.parse(json);
        return new VendorResponseDto(data.name);
    }
}