package com.mindmentor.model.response;

public record ServiceResponse (
        int serviceId,
        String name,
        String description,
        double price
) {
    public ServiceResponse(String name) {
        this(0, name, null, 0);
    }

    public ServiceResponse(int serviceId, String name, String description) {
        this(serviceId, name, description, 0);
    }

    public ServiceResponse(String name, String description, double price) {
        this(0, name, description, price);
    }
}