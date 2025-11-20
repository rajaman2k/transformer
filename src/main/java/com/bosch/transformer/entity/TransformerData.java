package com.bosch.transformer.entity;

import java.time.LocalDateTime;

public class TransformerData {
    private Integer transformerId;
    private String name;
    private LocalDateTime timestamp;
    private Double windingTemperature;
    private Double topOilTemperature;
    private Double humidity;
    private Double l1;
    private Double l2;
    private Double l3;

    // Constructors
    public TransformerData() {}

    public TransformerData(Integer transformerId, String name, LocalDateTime timestamp, 
                          Double windingTemperature, Double topOilTemperature, 
                          Double humidity, Double l1, Double l2, Double l3) {
        this.transformerId = transformerId;
        this.name = name;
        this.timestamp = timestamp;
        this.windingTemperature = windingTemperature;
        this.topOilTemperature = topOilTemperature;
        this.humidity = humidity;
        this.l1 = l1;
        this.l2 = l2;
        this.l3 = l3;
    }

    // Getters and Setters (generate these)
    public Integer getTransformerId() { return transformerId; }
    public void setTransformerId(Integer transformerId) { this.transformerId = transformerId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public Double getWindingTemperature() { return windingTemperature; }
    public void setWindingTemperature(Double windingTemperature) { this.windingTemperature = windingTemperature; }
    
    public Double getTopOilTemperature() { return topOilTemperature; }
    public void setTopOilTemperature(Double topOilTemperature) { this.topOilTemperature = topOilTemperature; }
    
    public Double getHumidity() { return humidity; }
    public void setHumidity(Double humidity) { this.humidity = humidity; }
    
    public Double getL1() { return l1; }
    public void setL1(Double l1) { this.l1 = l1; }
    
    public Double getL2() { return l2; }
    public void setL2(Double l2) { this.l2 = l2; }
    
    public Double getL3() { return l3; }
    public void setL3(Double l3) { this.l3 = l3; }
}