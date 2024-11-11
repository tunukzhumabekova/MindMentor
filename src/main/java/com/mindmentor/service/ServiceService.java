package com.mindmentor.service;

import com.mindmentor.model.request.ServiceCreateRequest;
import com.mindmentor.model.response.ServiceResponse;

import java.util.List;

public interface ServiceService {
    void createService(ServiceCreateRequest request);
    ServiceResponse getServiceById(int serviceId);
    List<ServiceResponse> getAllServices();
    void updateService(int serviceId, String name, String description, int usersId);
    void deleteService(int serviceId);
}