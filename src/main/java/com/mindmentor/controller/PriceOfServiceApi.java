package com.mindmentor.controller;

import com.example.public_.tables.records.PriceOfServiceRecord;
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
    @Operation(
            summary = "Create Price of Service",
            description = "Creates a new price record for a service and mentor."
    )
    public ResponseEntity<String> createPriceOfService(@RequestParam Double name,
                                                       @RequestParam Integer mentorId,
                                                       @RequestParam Integer serviceId) {
            priceOfServiceService.savePriceOfService(name, mentorId, serviceId);
            return ResponseEntity.ok("Price of service created successfully");
    }

    @GetMapping("/{priceOfServiceId}")
    @Operation(
            summary = "Get Price of Service by ID",
            description = "Fetches the details of a price record by its ID."
    )
    public ResponseEntity<Object> getPriceOfServiceById(@PathVariable Integer priceOfServiceId) {
            PriceOfServiceRecord record = priceOfServiceService.getPriceOfServiceById(priceOfServiceId);
            return ResponseEntity.ok(record);
    }

    @GetMapping("/all")
    @Operation(
            summary = "Get All Prices of Services",
            description = "Fetches a list of all price records."
    )
    public List<PriceOfServiceRecord> getAllPricesOfService() {
        return priceOfServiceService.getAllPricesOfService();
    }

    @PutMapping("/update/{priceOfServiceId}")
    @Operation(
            summary = "Update Price of Service",
            description = "Updates an existing price record for a service and mentor."
    )
    public ResponseEntity<String> updatePriceOfService(@PathVariable Integer priceOfServiceId,
                                                       @RequestParam Double price,
                                                       @RequestParam Integer mentorId,
                                                       @RequestParam Integer serviceId) {
            priceOfServiceService.updatePriceOfService(priceOfServiceId, price, mentorId, serviceId);
            return ResponseEntity.ok("Price of service updated successfully.");
    }

    @DeleteMapping("/delete/{priceOfServiceId}")
    @Operation(
            summary = "Delete Price of Service",
            description = "Deletes a price record by its ID."
    )
    public ResponseEntity<String> deletePriceOfService(@PathVariable Integer priceOfServiceId) {
            priceOfServiceService.deletePriceOfService(priceOfServiceId);
            return ResponseEntity.ok("Price of service deleted successfully.");
    }
}