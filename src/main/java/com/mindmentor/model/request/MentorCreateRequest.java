package com.mindmentor.model.request;

import com.fasterxml.jackson.annotation.JsonManagedReference;

public record MentorCreateRequest (
        Integer directionId,
        String language,
        Integer experience,
        String aboutMentor,
        String videoUrl,
        Integer usersId,
        @JsonManagedReference
        MentorProfileRequest profileRequest
) {
}