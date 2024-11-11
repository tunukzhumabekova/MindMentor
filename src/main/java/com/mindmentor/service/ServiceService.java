package com.mindmentor.service;

import com.example.public_.tables.records.ServiceRecord;
import com.mindmentor.model.request.ServiceCreateRequest;

import java.util.List;

public interface ServiceService {
    void createService(ServiceCreateRequest request);
    ServiceRecord getServiceById(Integer serviceId);
    List<ServiceRecord> getAllServices();
    void updateService(Integer serviceId, String name, String description, Integer usersId);
    void deleteService(Integer serviceId);
}