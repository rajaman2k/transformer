package com.bosch.transformer.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bosch.transformer.entity.TransformerData;
import com.bosch.transformer.service.TransformerDataService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/transformers")
@CrossOrigin(origins = "*")
public class TransformerDataController {

    @Autowired
    private TransformerDataService transformerDataService;

    // Get all data for a specific transformer
    @GetMapping("/{transformerId}/data")
    public ResponseEntity<List<TransformerData>> getTransformerData(
            @PathVariable Integer transformerId) {
        
        List<TransformerData> data = transformerDataService.getDataByTransformerId(transformerId);
        return ResponseEntity.ok(data);
    }

    // Get data for a transformer within a time frame
    @GetMapping("/{transformerId}/data/time-frame")
    public ResponseEntity<List<TransformerData>> getTransformerDataByTimeFrame(
            @PathVariable Integer transformerId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        
        List<TransformerData> data = transformerDataService.getDataByTransformerIdAndTimeFrame(
                transformerId, startDate, endDate);
        return ResponseEntity.ok(data);
    }

    // Add single transformer data
    @PostMapping("/data")
    public ResponseEntity<TransformerData> addTransformerData(@RequestBody TransformerData transformerData) {
        TransformerData savedData = transformerDataService.addTransformerData(transformerData);
        return ResponseEntity.ok(savedData);
    }

    // Add multiple transformer data
    @PostMapping("/data/batch")
    public ResponseEntity<List<TransformerData>> addMultipleTransformerData(@RequestBody List<TransformerData> transformerDataList) {
        List<TransformerData> savedData = transformerDataService.addMultipleTransformerData(transformerDataList);
        return ResponseEntity.ok(savedData);
    }

    // Optional: Get all data (for testing)
    @GetMapping("/data/all")
    public ResponseEntity<List<TransformerData>> getAllData() {
        List<TransformerData> allData = transformerDataService.getAllData();
        return ResponseEntity.ok(allData);
    }
}