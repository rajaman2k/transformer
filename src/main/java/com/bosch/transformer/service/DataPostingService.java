package com.bosch.transformer.service;

import com.bosch.transformer.config.ApiConfig;
import com.bosch.transformer.entity.TransformerData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class DataPostingService {
    
    private static final Logger logger = LoggerFactory.getLogger(DataPostingService.class);
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private ApiConfig apiConfig;
    
    public void postTransformerData(TransformerData data) {
        // Always post to internal storage (your own backend)
        postToInternal(data);
        
        // Also post to Bosch IoT Insights if configured
        if (apiConfig.isExternalUrlConfigured()) {
            postToBoschIoTInsights(data);
        }
    }
    
    private void postToInternal(TransformerData data) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<TransformerData> request = new HttpEntity<>(data, headers);
            
            ResponseEntity<String> response = restTemplate.postForEntity(
                apiConfig.getInternalUrl(), 
                request, 
                String.class
            );
            
            logger.info("✅ Internal storage - Posted data for {}. Response: {}", 
                       data.getName(), response.getStatusCode());
            
        } catch (Exception e) {
            logger.error("❌ Error posting to internal storage for {}: {}", 
                        data.getName(), e.getMessage());
        }
    }
    
    private void postToBoschIoTInsights(TransformerData data) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            // Add Basic Authentication
            if (apiConfig.isBasicAuthConfigured()) {
                String authHeader = apiConfig.getBasicAuthHeader();
                headers.set("Authorization", authHeader);
                logger.debug("🔐 Using Basic Auth with username: {}", apiConfig.getAuthUsername());
            } else {
                logger.warn("⚠️ No Basic Auth credentials configured for Bosch IoT Insights");
            }
            
            // Additional headers that might be required
            headers.set("X-Client-ID", apiConfig.getClientId());
            headers.set("User-Agent", "Transformer-Backend/1.0");
            headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
            
            // Transform data to Bosch IoT Insights expected format
            Object payload = createBoschIoTInsightsPayload(data);
            
            HttpEntity<Object> request = new HttpEntity<>(payload, headers);
            
            logger.info("🚀 Sending data to Bosch IoT Insights for transformer: {} (ID: {})", 
                       data.getName(), data.getTransformerId());
            
            ResponseEntity<String> response = restTemplate.postForEntity(
                apiConfig.getExternalUrl(), 
                request, 
                String.class
            );
            
            logger.info("🏭 Bosch IoT Insights - Successfully posted data! Response: {} - {}", 
                    response.getStatusCode().value(), 
                    ((HttpStatus) response.getStatusCode()).getReasonPhrase());
            
            // Log successful submission details
            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info("📊 Data submitted - Transformer: {}, Time: {}, Winding Temp: {}°C", 
                           data.getName(), data.getTimestamp().toLocalTime(), data.getWindingTemperature());
            }
            
            // Log response body for debugging (if any)
            if (response.getBody() != null && !response.getBody().isEmpty()) {
                logger.debug("Bosch IoT Response Body: {}", response.getBody());
            }
            
        } catch (Exception e) {
            logger.error("❌ Error posting to Bosch IoT Insights for transformer {}: {}", 
                        data.getName(), e.getMessage());
            
            // More detailed error logging for troubleshooting
            if (e.getMessage().contains("401")) {
                logger.error("🔐 Authentication failed - Please check your API username and password");
            } else if (e.getMessage().contains("403")) {
                logger.error("🚫 Access forbidden - Check if API user has proper permissions for project {}", "xyz");
            } else if (e.getMessage().contains("404")) {
                logger.error("🔍 Endpoint not found - Verify the URL: {}", apiConfig.getExternalUrl());
            }
        }
    }
    
    /**
     * Create payload in Bosch IoT Insights expected format
     */
    private Object createBoschIoTInsightsPayload(TransformerData data) {
        Map<String, Object> payload = new HashMap<>();
        
        // Device identification
        payload.put("deviceId", "transformer_" + data.getTransformerId());
        payload.put("deviceName", data.getName());
        payload.put("timestamp", data.getTimestamp().toString()); // ISO 8601 format
        
        // Sensor readings - using common IoT field names
        Map<String, Object> measurements = new HashMap<>();
        measurements.put("winding_temperature_c", data.getWindingTemperature());
        measurements.put("oil_temperature_c", data.getTopOilTemperature());
        measurements.put("humidity_percent", data.getHumidity());
        measurements.put("current_l1_a", data.getL1());
        measurements.put("current_l2_a", data.getL2());
        measurements.put("current_l3_a", data.getL3());
        
        payload.put("measurements", measurements);
        
        // Add metadata
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("source", "spring-boot-backend");
        metadata.put("version", "1.0");
        metadata.put("dataType", "transformer_telemetry");
        metadata.put("project", "xyz");
        
        payload.put("metadata", metadata);
        
        logger.debug("📦 Prepared payload for Bosch IoT: {}", payload);
        return payload;
    }
}