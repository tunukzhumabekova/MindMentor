package com.mindmentor.model.response;

import java.util.List;

public record MentorProfileResponse (
        String fio,
        String image,
        String directionName,
        String language,
        int experience,
        String aboutMentor,
        String videoUrl,
        List<CourseResponse> courses,
        List<ServiceResponse> services
){
}