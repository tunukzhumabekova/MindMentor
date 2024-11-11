package com.mindmentor.model.response;

public record PriceOfServiceResponse (
        int priceOfServiceId,
        double price,
        int mentorId,
        int serviceId
) {
}