package com.mindmentor.controller;

import com.mindmentor.model.request.UserCreateRequest;
import com.mindmentor.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User API", description = "User endpoints")
public class UserApi {
    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new user", description = "Allows administrators to create a new user.")
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserCreateRequest request) {
        userService.createUser(request);
        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update existing user", description = "Allows administrators to update details of an existing user.")
    @PutMapping
    public ResponseEntity<String> updateUser(@RequestBody UserCreateRequest request) {
        userService.updateUser(request);
        return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a user", description = "Allows administrators to delete a user by their email address.")
    @DeleteMapping("/{email}")
    public ResponseEntity<String> deleteUser(@PathVariable String email) {
        userService.deleteUser(email);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Block a user", description = "Allows administrators to block a user by their email address.")
    @PostMapping("/block/{email}")
    public ResponseEntity<String> blockUser(@PathVariable String email) {
        userService.blockUser(email);
        return new ResponseEntity<>("User blocked successfully", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Unblock a user", description = "Allows administrators to unblock a user by their email address.")
    @PostMapping("/unblock/{email}")
    public ResponseEntity<String> unblockUser(@PathVariable String email) {
        userService.unblockUser(email);
        return new ResponseEntity<>("User unblocked successfully", HttpStatus.OK);
    }
}