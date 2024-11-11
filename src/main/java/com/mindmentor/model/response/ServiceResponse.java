package com.mindmentor.model.response;

import com.fasterxml.jackson.annotation.JsonBackReference;

public record ServiceResponse (
        String name,
        String description,
        double price,
        @JsonBackReference
        MentorProfileResponse mentorProfileResponse
) {
        public ServiceResponse(String name, String description, Double price) {
                this(name, description, price, null);
        }
}