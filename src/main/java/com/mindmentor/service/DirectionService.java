package com.mindmentor.service;

import com.mindmentor.model.response.DirectionResponse;

import java.util.List;

public interface DirectionService {
    int createDirection(String name);
    DirectionResponse getDirectionById(int directionId);
    List<DirectionResponse> getAllDirections();
    int updateDirection(int directionId, String name);
    int deleteDirection(int directionId);
}