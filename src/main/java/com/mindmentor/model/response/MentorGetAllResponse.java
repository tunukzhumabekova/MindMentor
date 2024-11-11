package com.mindmentor.model.response;

import java.util.List;

public record MentorGetAllResponse (
        String fio,
        String image,
        String directionName,
        int experience,
        String aboutMentor,
        String fileUrl,
        List<ServiceResponse> services,
        double rating,
        double price
) {
}