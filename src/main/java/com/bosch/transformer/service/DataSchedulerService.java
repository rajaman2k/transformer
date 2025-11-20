package com.bosch.transformer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.bosch.transformer.entity.TransformerData;

@Service
public class DataSchedulerService {
    
    private static final Logger logger = LoggerFactory.getLogger(DataSchedulerService.class);
    
    @Autowired
    private RandomDataGeneratorService dataGeneratorService;
    
    @Autowired
    private DataPostingService dataPostingService;
    
    // Run every minute (60,000 milliseconds) - sends only ONE object
    @Scheduled(fixedRate = 15000)
    public void scheduleSingleDataPosting() {
        logger.info("🕒 Scheduled task started - Generating and posting single transformer data");
        
        try {
            // Generate ONLY ONE random transformer data record
            TransformerData randomData = dataGeneratorService.generateRandomTransformerData();
            
            logger.info("📊 Generated data - Transformer: {}, Time: {}, Temp: {}°C", 
                       randomData.getName(), 
                       randomData.getTimestamp().toLocalTime(),
                       randomData.getWindingTemperature());
            
            // Post the single record
            dataPostingService.postTransformerData(randomData);
            
            logger.info("✅ Scheduled task completed - Single data posted successfully");
            
        } catch (Exception e) {
            logger.error("❌ Error in scheduled task: {}", e.getMessage());
        }
    }
}

