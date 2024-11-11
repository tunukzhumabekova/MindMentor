package com.mindmentor.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserCreateRequest (
        @NotBlank
        String fio,
        @NotBlank(message = "Email is mandatory")
        @Email(message = "Email should be valid")
        String email,
        @NotBlank
        String phone_number,
        @NotBlank
        String password
) {
}