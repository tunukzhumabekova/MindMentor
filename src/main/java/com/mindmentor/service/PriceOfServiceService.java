package com.mindmentor.service;

import com.mindmentor.model.response.PriceOfServiceResponse;

import java.util.List;

public interface PriceOfServiceService {
    void savePriceOfService(double price, int mentorId, int serviceId);
    PriceOfServiceResponse getPriceOfServiceById(int priceOfServiceId);
    List<PriceOfServiceResponse> getAllPricesOfService();
    void updatePriceOfService(int priceOfServiceId, double price, int mentorId, int serviceId);
    void deletePriceOfService(int priceOfServiceId);
}