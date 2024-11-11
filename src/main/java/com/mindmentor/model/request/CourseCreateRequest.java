package com.mindmentor.model.request;

public record CourseCreateRequest (
        Integer mentorId,
        String file_url,
        String course_name,
        String course_description,
        String what_you_will_learn,
        Double price,
        String language,
        Integer directionId
) {}
