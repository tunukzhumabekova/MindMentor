package com.mindmentor.model.request;

import com.example.public_.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignUp(
        @NotBlank(message = "Email is mandatory")
        @Email(message = "Email should be valid")
        String email,

        @NotBlank(message = "Password is mandatory")
        String password,

        @NotBlank(message = "Fio is mandatory")
        String fio,

        @NotNull(message = "Role is mandatory")
        Role role
){}