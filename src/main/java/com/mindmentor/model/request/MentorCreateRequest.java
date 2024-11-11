package com.mindmentor.model.request;

import com.fasterxml.jackson.annotation.JsonManagedReference;

public record MentorCreateRequest (
        int directionId,
        String language,
        int experience,
        String aboutMentor,
        String videoUrl,
        int usersId,
        @JsonManagedReference
        MentorProfileRequest profileRequest
) {
}