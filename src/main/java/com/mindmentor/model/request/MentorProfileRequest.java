package com.mindmentor.model.request;

import com.fasterxml.jackson.annotation.JsonBackReference;

public record MentorProfileRequest(
        String fio,
        String email,
        String phoneNumber,
        String password,
        @JsonBackReference
        MentorCreateRequest mentorCreateRequest
) {
}