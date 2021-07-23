package com.example.famousventureempire.services.util;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class CloudinaryConfig {
    @Value("${CLOUD_NAME}")
    private String cloudName;
    @Value("${API_KEY}")
    private String apikey;
    @Value("${API_SECRET}")
    private String secretKey;
}
