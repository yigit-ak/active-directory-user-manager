package net.yigitak.ad_user_manager.mappers;

import net.yigitak.ad_user_manager.dto.UserCreateDto;
import net.yigitak.ad_user_manager.models.User;
import org.springframework.beans.factory.annotation.Value;

public class userDtoMapper {

    @Value("${parent-organizational-unit}")
    private String PARENT_ORGANIZATIONAL_UNIT;

    @Value("${user-creation-description}")
    private String USER_CREATION_DESCRIPTION;

    public User dtoToUser(UserCreateDto dto) {
        User user = new User();
        String fullName = dto.firstName() + ' ' + dto.lastName();
        String organizationalUnit = "ou=%s,ou=%s".formatted(PARENT_ORGANIZATIONAL_UNIT, fullName);

        user.setOrganizationalUnit(organizationalUnit);
        user.setCommonName(fullName);
        user.setDescription(USER_CREATION_DESCRIPTION);
        user.setSamAccountName("MAMA");
        user.setDisplayName(fullName);
        user.setGivenName(dto.firstName());
        user.setEmail(dto.email());
        user.setSurname(dto.lastName());
        user.setTelephoneNumber(dto.phoneNumber());
        user.setUserPassword("MAMA");
        user.setUserPrincipalName("MAMA");

        return user;
    }
}
