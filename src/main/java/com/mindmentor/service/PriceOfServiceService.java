package com.mindmentor.service;

import com.example.public_.tables.records.PriceOfServiceRecord;

import java.util.List;

public interface PriceOfServiceService {
    Integer savePriceOfService(Double price, Integer mentorId, Integer serviceId);
    PriceOfServiceRecord getPriceOfServiceById(Integer priceOfServiceId);
    List<PriceOfServiceRecord> getAllPricesOfService();
    Integer updatePriceOfService(Integer priceOfServiceId, Double price, Integer mentorId, Integer serviceId);
    Integer deletePriceOfService(Integer priceOfServiceId);
}