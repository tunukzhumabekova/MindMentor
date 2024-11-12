package com.mindmentor.controller;

import com.mindmentor.model.response.PriceOfServiceResponse;
import com.mindmentor.service.PriceOfServiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/prices")
@Tag(name = "Price of Service API", description = "Endpoints for managing prices of services")
public class PriceOfServiceApi {

    private final PriceOfServiceService priceOfServiceService;

    @PostMapping("/create")
    @Operation(summary = "Create Price of Service", description = "Creates a new price record for a service and mentor.")
    public ResponseEntity<String> createPriceOfService(@RequestParam double name,
                                                       @RequestParam int mentorId,
                                                       @RequestParam int serviceId) {
            priceOfServiceService.savePriceOfService(name, mentorId, serviceId);
            return ResponseEntity.ok("Price of service created successfully");
    }

    @GetMapping("/{priceOfServiceId}")
    @Operation(summary = "Get Price of Service by ID", description = "Fetches the details of a price record by its ID.")
    public PriceOfServiceResponse getPriceOfServiceById(@PathVariable int priceOfServiceId) {
            return priceOfServiceService.getPriceOfServiceById(priceOfServiceId);
    }

    @GetMapping("/all")
    @Operation(summary = "Get All Prices of Services", description = "Fetches a list of all price records.")
    public List<PriceOfServiceResponse> getAllPricesOfService() {
        return priceOfServiceService.getAllPricesOfService();
    }

    @PutMapping("/update/{priceOfServiceId}")
    @Operation(summary = "Update Price of Service", description = "Updates an existing price record for a service and mentor.")
    public ResponseEntity<String> updatePriceOfService(@PathVariable int priceOfServiceId,
                                                       @RequestParam double price,
                                                       @RequestParam int mentorId,
                                                       @RequestParam int serviceId) {
            priceOfServiceService.updatePriceOfService(priceOfServiceId, price, mentorId, serviceId);
            return ResponseEntity.ok("Price of service updated successfully.");
    }

    @DeleteMapping("/delete/{priceOfServiceId}")
    @Operation(summary = "Delete Price of Service", description = "Deletes a price record by its ID.")
    public ResponseEntity<String> deletePriceOfService(@PathVariable int priceOfServiceId) {
            priceOfServiceService.deletePriceOfService(priceOfServiceId);
            return ResponseEntity.ok("Price of service deleted successfully.");
    }
}