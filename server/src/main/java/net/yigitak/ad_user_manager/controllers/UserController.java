package net.yigitak.ad_user_manager.controllers;

import net.yigitak.ad_user_manager.dto.UserCreateDto;
import net.yigitak.ad_user_manager.dto.UserResponseDto;
import net.yigitak.ad_user_manager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{cn}")
    public ResponseEntity<UserResponseDto> getUserByCn(@PathVariable String cn) {
        UserResponseDto user = userService.findUserByCn(cn);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserCreateDto userDto) {
        userService.createNewUser(userDto);
        URI location = URI.create("/api/v1/users/" + userDto.firstName()); // todo: return common name
        return ResponseEntity.created(location).body("User created successfully.");
    }

    @PutMapping("/{cn}/reset-password")
    public ResponseEntity<String> resetPassword(@PathVariable String cn) {
        userService.resetPassword(cn);
        return ResponseEntity.ok("Password reset successfully.");
    }

    @PutMapping("/{cn}/lock")
    public ResponseEntity<String> lockUser(@PathVariable String cn) {
        userService.lockUser(cn);
        return ResponseEntity.ok("User locked successfully.");
    }

    @PutMapping("/{cn}/unlock")
    public ResponseEntity<String> unlockUser(@PathVariable String cn) {
        userService.unlockUser(cn);
        return ResponseEntity.ok("User unlocked successfully.");
    }
}
