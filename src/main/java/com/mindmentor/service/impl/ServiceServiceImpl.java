package com.mindmentor.service.impl;

import com.example.public_.tables.records.ServiceRecord;
import com.mindmentor.model.request.ServiceCreateRequest;
import com.mindmentor.repository.ServiceRepository;
import com.mindmentor.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceServiceImpl implements ServiceService {
    private final ServiceRepository serviceRepository;

    @Override
    public void createService(ServiceCreateRequest request) {
        serviceRepository.createService(request);
    }

    @Override
    public ServiceRecord getServiceById(Integer serviceId) {
        return serviceRepository.getServiceById(serviceId);
    }

    @Override
    public List<ServiceRecord> getAllServices() {
        return serviceRepository.getAllServices();
    }

    @Override
    public void updateService(Integer serviceId, String name, String description, Integer usersId) {
        serviceRepository.updateService(serviceId, name, description, usersId);
    }

    @Override
    public void deleteService(Integer serviceId) {
        serviceRepository.deleteService(serviceId);
    }
}