package com.mindmentor.service.impl;

import com.example.public_.tables.records.DirectionRecord;
import com.mindmentor.exceptions.NotFoundException;
import com.mindmentor.model.response.DirectionResponse;
import com.mindmentor.repository.DirectionRepository;
import com.mindmentor.service.DirectionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectionServiceImpl implements DirectionService {

    private final DirectionRepository directionRepository;

    public DirectionServiceImpl(DirectionRepository directionRepository) {
        this.directionRepository = directionRepository;
    }

    @Override
    public int createDirection(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        return directionRepository.createDirection(name);
    }

    @Override
    public DirectionResponse getDirectionById(int directionId) {
        if (directionId <= 0) {
            throw new IllegalArgumentException("Direction ID must be positive");
        }
        DirectionResponse direction = directionRepository.getDirectionById(directionId);
        if (direction == null) {
            throw new NotFoundException("Direction with ID " + directionId + " not found");
        }
        return direction;
    }

    @Override
    public List<DirectionResponse> getAllDirections() {
        return directionRepository.getAllDirections();
    }

    @Override
    public int updateDirection(int directionId, String name) {
        if (directionId <= 0) {
            throw new IllegalArgumentException("Direction ID must be positive");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        return directionRepository.updateDirection(directionId, name);
    }

    @Override
    public int deleteDirection(int directionId) {
        if (directionId <= 0) {
            throw new IllegalArgumentException("Direction ID must be positive");
        }
        return directionRepository.deleteDirection(directionId);
    }
}