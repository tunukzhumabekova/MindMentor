package com.mindmentor.controller;

import com.example.public_.tables.records.ServiceRecord;
import com.mindmentor.model.request.ServiceCreateRequest;
import com.mindmentor.service.ServiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/services")
@Tag(name = "Service API", description = "Endpoints for managing services")
public class ServiceApi {

    private final ServiceService service;

    @PostMapping("/create")
    @Operation(
            summary = "Create Service",
            description = "Creates a new service with the provided details."
    )
    public ResponseEntity<String> createService(@Valid @RequestBody ServiceCreateRequest request) {
            service.createService(request);
            return ResponseEntity.ok("Service created successfully.");
    }

    @GetMapping("/{serviceId}")
    @Operation(
            summary = "Get Service by ID",
            description = "Fetches the details of a specific service by its ID."
    )
    public ResponseEntity<Object> getServiceById(@PathVariable Integer serviceId) {
            return ResponseEntity.ok(service.getServiceById(serviceId));
    }

    @GetMapping("/all")
    @Operation(
            summary = "Get All Services",
            description = "Fetches the list of all services."
    )
    public List<ServiceRecord> getAllServices() {
        return service.getAllServices();
    }

    @PutMapping("/update/{serviceId}")
    @Operation(
            summary = "Update Service",
            description = "Updates the details of an existing service."
    )
    public ResponseEntity<String> updateService(@PathVariable Integer serviceId,
                                                @RequestParam String name,
                                                @RequestParam String description,
                                                @RequestParam Integer usersId) {
            service.updateService(serviceId, name, description, usersId);
            return ResponseEntity.ok("Service updated successfully.");
    }

    @DeleteMapping("/delete/{serviceId}")
    @Operation(
            summary = "Delete Service",
            description = "Deletes an existing service by its ID."
    )
    public ResponseEntity<String> deleteService(@PathVariable Integer serviceId) {
            service.deleteService(serviceId);
            return ResponseEntity.ok("Service deleted successfully.");
    }
}