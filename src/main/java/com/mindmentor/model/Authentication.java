package com.mindmentor.model;

import com.example.public_.enums.Role;

public record Authentication(
        int id,
        String username,
        String token,
        Role role
) {
}