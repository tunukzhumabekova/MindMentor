package com.mindmentor.model.request;

public record ServiceCreateRequest (
        Integer mentorId,
        String service_name,
        String service_description,
        Double price,
        Integer userInfoId
) {
}