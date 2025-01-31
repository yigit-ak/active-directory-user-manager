package net.yigitak.ad_user_manager.controllers;

import net.yigitak.ad_user_manager.dto.UserCreateDto;
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

    @GetMapping("/{commonName}")
    public ResponseEntity<?> getUser(@PathVariable String commonName) {
        var userResponse = userService.findUserByCn(commonName);
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserCreateDto dto, UriComponentsBuilder uriBuilder) {
        String newUserCommonName = userService.createUser(dto);
        URI location = uriBuilder.path("/users/{id}").buildAndExpand(newUserCommonName).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{commonName}/reset-password")
    public ResponseEntity<?> resetPassword(@PathVariable String commonName) {
        userService.resetPassword(commonName);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{commonName}/lock")
    public ResponseEntity<?> lockUserAccount(@PathVariable String commonName) {
        userService.lockUser(commonName);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{commonName}/unlock")
    public ResponseEntity<?> unlockUserAccount(@PathVariable String commonName) {
        userService.unlockUser(commonName);
        return ResponseEntity.ok().build();
    }

}
