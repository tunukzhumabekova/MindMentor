package com.mindmentor.model.request;

public record ServiceCreateRequest (
        int mentorId,
        String service_name,
        String service_description,
        double price,
        int userInfoId
) {
}