package com.bosch.transformer.service;

import org.springframework.stereotype.Service;

import com.bosch.transformer.entity.TransformerData;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransformerDataService {

    // Hardcoded mock data
    private List<TransformerData> mockData = createMockData();

    // Method 1: Get all data for a specific transformer by ID
    public List<TransformerData> getDataByTransformerId(Integer transformerId) {
        return mockData.stream()
                .filter(data -> data.getTransformerId().equals(transformerId))
                .collect(Collectors.toList());
    }

    // Method 2: Get data for a specific transformer within a time frame
    public List<TransformerData> getDataByTransformerIdAndTimeFrame(
            Integer transformerId, 
            LocalDateTime startDate, 
            LocalDateTime endDate) {
        
        return mockData.stream()
                .filter(data -> data.getTransformerId().equals(transformerId))
                .filter(data -> !data.getTimestamp().isBefore(startDate) && 
                               !data.getTimestamp().isAfter(endDate))
                .collect(Collectors.toList());
    }

    // Method 3: Add new data to mock dataset
    public TransformerData addTransformerData(TransformerData transformerData) {
        mockData.add(transformerData);
        return transformerData;
    }

    // Method 4: Add multiple data entries
    public List<TransformerData> addMultipleTransformerData(List<TransformerData> transformerDataList) {
        mockData.addAll(transformerDataList);
        return transformerDataList;
    }

    // Create hardcoded mock data
    private List<TransformerData> createMockData() {
        List<TransformerData> data = new ArrayList<>();
        
        // Transformer 1 data
        data.add(new TransformerData(1, "Transformer_1", 
            LocalDateTime.of(2025, 1, 16, 10, 30, 0), 
            65.5, 52.1, 45.0, 120.5, 118.3, 119.8));
        
//        data.add(new TransformerData(1, "Transformer_1", 
//            LocalDateTime.of(2025, 1, 16, 11, 0, 0), 
//            67.2, 53.8, 43.2, 122.1, 119.6, 121.3));
//        
//        data.add(new TransformerData(1, "Transformer_1", 
//            LocalDateTime.of(2025, 1, 16, 11, 30, 0), 
//            69.8, 55.4, 41.8, 125.7, 123.2, 124.5));

        // Transformer 2 data
        data.add(new TransformerData(2, "Transformer_2", 
            LocalDateTime.of(2025, 1, 16, 10, 30, 0), 
            62.3, 48.7, 52.1, 115.8, 113.4, 114.9));
        
//        data.add(new TransformerData(2, "Transformer_2", 
//            LocalDateTime.of(2025, 1, 16, 11, 0, 0), 
//            64.1, 50.2, 50.8, 117.2, 115.1, 116.7));

        // Transformer 3 data
        data.add(new TransformerData(3, "Transformer_3", 
            LocalDateTime.of(2025, 1, 16, 10, 30, 0), 
            71.2, 57.9, 38.4, 128.6, 126.3, 127.8));
        
        // Add more mock data as needed...
        
        return data;
    }

    // Optional: Get all data (for debugging)
    public List<TransformerData> getAllData() {
        return new ArrayList<>(mockData);
    }
}