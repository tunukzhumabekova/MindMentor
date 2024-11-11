package com.mindmentor.model.response;

import com.example.public_.tables.records.ServiceRecord;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

public record MentorGetAllResponse (
        String fio,
        String image,
        String directionName,
        int experience,
        String aboutMentor,
        String fileUrl,
        @JsonManagedReference
        List<ServiceRecord> services,
        double rating,
        double price
) {
}