package com.mindmentor.service.impl;

import com.mindmentor.exceptions.AlreadyExistsException;
import com.mindmentor.model.response.PriceOfServiceResponse;
import com.mindmentor.repository.PriceOfServiceRepository;
import com.mindmentor.service.PriceOfServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceOfServiceServiceImpl implements PriceOfServiceService {

    private final PriceOfServiceRepository priceOfServiceRepository;

    @Override
    public void savePriceOfService(double price, int mentorId, int serviceId) {
        PriceOfServiceResponse priceOfServiceResponse =
                priceOfServiceRepository.getPriceOfServiceById(serviceId);
        if (priceOfServiceResponse != null) {
            throw new AlreadyExistsException("Price of service already exists.");
        }

        priceOfServiceRepository.savePriceOfService(price, mentorId, serviceId);
    }

    @Override
    public PriceOfServiceResponse getPriceOfServiceById(int priceOfServiceId) {
        PriceOfServiceResponse priceOfServiceResponse =
                priceOfServiceRepository.getPriceOfServiceById(priceOfServiceId);
        if (priceOfServiceResponse == null) {
            throw new IllegalArgumentException("Price of service not found.");
        }
        return priceOfServiceResponse;
    }

    @Override
    public List<PriceOfServiceResponse> getAllPricesOfService() {
        return priceOfServiceRepository.getAllPricesOfService();
    }

    @Override
    public void updatePriceOfService(int priceOfServiceId, double price, int mentorId, int serviceId) {
        PriceOfServiceResponse priceOfServiceResponse =
                priceOfServiceRepository.getPriceOfServiceById(priceOfServiceId);
        if (priceOfServiceResponse == null) {
            throw new IllegalArgumentException("Price of service not found for update.");
        }

        priceOfServiceRepository.updatePriceOfService(priceOfServiceId, price, mentorId, serviceId);
    }

    @Override
    public void deletePriceOfService(int priceOfServiceId) {
        PriceOfServiceResponse priceOfServiceResponse =
                priceOfServiceRepository.getPriceOfServiceById(priceOfServiceId);
        if (priceOfServiceResponse == null) {
            throw new IllegalArgumentException("Price of service not found for update.");
        }

        priceOfServiceRepository.deletePriceOfService(priceOfServiceId);
    }
}