package com.mindmentor.controller;

import com.mindmentor.model.response.DirectionResponse;
import com.mindmentor.service.DirectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/directions")
@Tag(name = "Direction API", description = "Endpoints for direction management")
@RequiredArgsConstructor
public class DirectionApi {

    private final DirectionService directionService;

    @PostMapping("/")
    @Operation(summary = "Create Direction", description = "Creates a new direction.")
    public int createDirection(@RequestParam String name) {
        return directionService.createDirection(name);
    }

    @GetMapping("/{directionId}")
    @Operation(summary = "Get Direction by ID", description = "Retrieves a direction by its ID.")
    public DirectionResponse getDirectionById(@PathVariable int directionId) {
        return directionService.getDirectionById(directionId);
    }

    @GetMapping("/")
    @Operation(summary = "Get All Directions", description = "Retrieves all directions.")
    public List<DirectionResponse> getAllDirections() {
        return directionService.getAllDirections();
    }

    @PutMapping("/{directionId}")
    @Operation(summary = "Update Direction", description = "Updates the name of an existing direction.")
    public int updateDirection(@PathVariable int directionId, @RequestParam String name) {
        return directionService.updateDirection(directionId, name);
    }

    @DeleteMapping("/{directionId}")
    @Operation(summary = "Delete Direction", description = "Deletes a direction by its ID.")
    public int deleteDirection(@PathVariable int directionId) {
        return directionService.deleteDirection(directionId);
    }
}