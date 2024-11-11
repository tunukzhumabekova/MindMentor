package com.mindmentor.service.impl;

import com.mindmentor.model.request.ServiceCreateRequest;
import com.mindmentor.model.response.ServiceResponse;
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
    public ServiceResponse getServiceById(int serviceId) {
        return serviceRepository.getServiceById(serviceId);
    }

    @Override
    public List<ServiceResponse> getAllServices() {
        return serviceRepository.getAllServices();
    }

    @Override
    public void updateService(int serviceId, String name, String description, int usersId) {
        serviceRepository.updateService(serviceId, name, description, usersId);
    }

    @Override
    public void deleteService(int serviceId) {
        serviceRepository.deleteService(serviceId);
    }
}