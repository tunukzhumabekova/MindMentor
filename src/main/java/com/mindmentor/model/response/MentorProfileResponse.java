package com.mindmentor.model.response;

import com.example.public_.tables.records.CoursesRecord;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

public record MentorProfileResponse (
        String fio,
        String image,
        String directionName,
        String language,
        int experience,
        String aboutMentor,
        String videoUrl,
        @JsonManagedReference
        List<CoursesRecord> courses,
        @JsonManagedReference
        List<ServiceResponse> services
){
}