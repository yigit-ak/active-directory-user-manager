package net.yigitak.ad_user_manager.controllers;

import net.yigitak.ad_user_manager.dto.UserCreateDto;
import net.yigitak.ad_user_manager.dto.UserResponseDto;
import net.yigitak.ad_user_manager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{cn}")
    public ResponseEntity<UserResponseDto> getUserByCn(@PathVariable String cn) {
        UserResponseDto user = userService.findUserByCn(cn);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserCreateDto userDto) {
        try {
            userService.createNewUser(userDto);
            return ResponseEntity.ok("User created successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to create user: " + e.getMessage());
        }
    }

    @PutMapping("/{cn}/reset-password")
    public ResponseEntity<String> resetPassword(@PathVariable String cn) {
        try {
            userService.resetPassword(cn);
            return ResponseEntity.ok("Password reset successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("User not found: " + cn);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to reset password: " + e.getMessage());
        }
    }

    @PutMapping("/{cn}/lock")
    public ResponseEntity<String> lockUser(@PathVariable String cn) {
        try {
            userService.lockUser(cn);
            return ResponseEntity.ok("User locked successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("User not found: " + cn);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to lock user: " + e.getMessage());
        }
    }


    @PutMapping("/{cn}/unlock")
    public ResponseEntity<String> unlockUser(@PathVariable String cn) {
        try {
            userService.unlockUser(cn);
            return ResponseEntity.ok("User unlocked successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("User not found: " + cn);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to unlock user: " + e.getMessage());
        }
    }


}
