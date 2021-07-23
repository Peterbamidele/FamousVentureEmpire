package com.example.famousventureempire;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.famousventureempire.services.util.CloudinaryConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FamousVentureEmpireApplication {
    @Autowired
    CloudinaryConfig cloudinaryConfig;

    public static void main(String[] args) {
        SpringApplication.run(FamousVentureEmpireApplication.class, args);
    }
    @Bean
    public Cloudinary getCloudinary(){
        return new Cloudinary(ObjectUtils.asMap("cloud_name",cloudinaryConfig.getCloudName(),
                "api_key",cloudinaryConfig.getApikey(),"api_secret",cloudinaryConfig.getSecretKey()));
    }

}
