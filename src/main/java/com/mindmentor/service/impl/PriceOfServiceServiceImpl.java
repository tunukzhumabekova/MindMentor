package com.mindmentor.service.impl;

import com.example.public_.tables.records.PriceOfServiceRecord;
import com.mindmentor.repository.PriceOfServiceRepository;
import com.mindmentor.service.PriceOfServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PriceOfServiceServiceImpl implements PriceOfServiceService {

    private final PriceOfServiceRepository priceOfServiceRepository;

    @Override
    public Integer savePriceOfService(Double price, Integer mentorId, Integer serviceId) {
        Optional<PriceOfServiceRecord> existingPrice = priceOfServiceRepository.getAllPricesOfService()
                .stream()
                .filter(record -> record.getMentorId().equals(mentorId) && record.getServiceId().equals(serviceId))
                .findFirst();

        if (existingPrice.isPresent()) {
            throw new IllegalArgumentException("Price for the given mentor and service already exists.");
        }

        return priceOfServiceRepository.savePriceOfService(price, mentorId, serviceId);
    }

    @Override
    public PriceOfServiceRecord getPriceOfServiceById(Integer priceOfServiceId) {
        PriceOfServiceRecord record = priceOfServiceRepository.getPriceOfServiceById(priceOfServiceId);
        if (record == null) {
            throw new IllegalArgumentException("Price of service not found.");
        }
        return record;
    }

    @Override
    public List<PriceOfServiceRecord> getAllPricesOfService() {
        return priceOfServiceRepository.getAllPricesOfService();
    }

    @Override
    public Integer updatePriceOfService(Integer priceOfServiceId, Double price, Integer mentorId, Integer serviceId) {
        PriceOfServiceRecord record = priceOfServiceRepository.getPriceOfServiceById(priceOfServiceId);
        if (record == null) {
            throw new IllegalArgumentException("Price of service not found for update.");
        }

        return priceOfServiceRepository.updatePriceOfService(priceOfServiceId, price, mentorId, serviceId);
    }

    @Override
    public Integer deletePriceOfService(Integer priceOfServiceId) {
        PriceOfServiceRecord record = priceOfServiceRepository.getPriceOfServiceById(priceOfServiceId);
        if (record == null) {
            throw new IllegalArgumentException("Price of service not found for deletion.");
        }

        return priceOfServiceRepository.deletePriceOfService(priceOfServiceId);
    }
}