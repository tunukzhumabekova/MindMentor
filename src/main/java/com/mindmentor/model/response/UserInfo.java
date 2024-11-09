package com.mindmentor.model.response;

import com.example.public_.enums.Role;

public record UserInfo(
        Integer id,
        String email,
        Role role,
        String password
) {
}