package com.mindmentor.service;

import com.example.public_.tables.records.DirectionRecord;

import java.util.List;

public interface DirectionService {
    int createDirection(String name);
    DirectionRecord getDirectionById(int directionId);
    List<DirectionRecord> getAllDirections();
    int updateDirection(int directionId, String name);
    int deleteDirection(int directionId);
}