package com.mindmentor.model.request;

public record CourseCreateRequest (
        int mentorId,
        String file_url,
        String course_name,
        String course_description,
        String what_you_will_learn,
        double price,
        String language,
        int directionId
) {}
