package com.bosch.transformer.service;

import com.bosch.transformer.entity.TransformerData;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class RandomDataGeneratorService {
    
    private final Random random = new Random();
    private final String[] transformerNames = {"Transformer_1", "Transformer_2", "Transformer_3", "Transformer_4", "Transformer_5"};
    
    public TransformerData generateRandomTransformerData() {
        Integer transformerId = random.nextInt(5) + 1; // IDs from 1 to 5
        String name = transformerNames[transformerId - 1];
        LocalDateTime timestamp = LocalDateTime.now();
        
        // Generate realistic random values within typical ranges
        Double windingTemperature = 60.0 + random.nextDouble() * 20; // 60-80°C
        Double topOilTemperature = 45.0 + random.nextDouble() * 15;  // 45-60°C
        Double humidity = 30.0 + random.nextDouble() * 40;           // 30-70%
        Double l1 = 100.0 + random.nextDouble() * 40;               // 100-140
        Double l2 = 100.0 + random.nextDouble() * 40;               // 100-140
        Double l3 = 100.0 + random.nextDouble() * 40;               // 100-140
        
        return new TransformerData(transformerId, name, timestamp, 
                                 windingTemperature, topOilTemperature, 
                                 humidity, l1, l2, l3);
    }
}