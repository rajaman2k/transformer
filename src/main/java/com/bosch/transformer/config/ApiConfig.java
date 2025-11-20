package com.bosch.transformer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import java.util.Base64;

@Configuration
public class ApiConfig {
    
    @Value("${transformer.data.internal.url:http://localhost:8080/api/transformers/data}")
    private String internalUrl;
    
    @Value("${transformer.data.external.url:}")
    private String externalUrl;
    
    @Value("${transformer.data.external.auth.username:}")
    private String authUsername;
    
    @Value("${transformer.data.external.auth.password:}")
    private String authPassword;
    
    @Value("${transformer.data.external.client.id:transformer-monitoring-system}")
    private String clientId;
    
    public String getInternalUrl() {
        return internalUrl;
    }
    
    public String getExternalUrl() {
        return externalUrl;
    }
    
    public String getAuthUsername() {
        return authUsername;
    }
    
    public String getAuthPassword() {
        return authPassword;
    }
    
    public String getClientId() {
        return clientId;
    }
    
    public boolean isExternalUrlConfigured() {
        return externalUrl != null && !externalUrl.trim().isEmpty();
    }
    
    public boolean isBasicAuthConfigured() {
        return authUsername != null && !authUsername.trim().isEmpty() 
            && authPassword != null && !authPassword.trim().isEmpty();
    }
    
    // Helper method to generate Basic Auth header value
    public String getBasicAuthHeader() {
        if (!isBasicAuthConfigured()) {
            return null;
        }
        String credentials = authUsername + ":" + authPassword;
        return "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());
    }
}