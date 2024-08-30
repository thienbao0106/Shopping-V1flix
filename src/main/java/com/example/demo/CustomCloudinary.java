package com.example.demo;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;





@Configuration
@Component
@Getter
@PropertySource(value = "classpath*:application.properties")

public class CustomCloudinary {


    public static String cloudName;

    public static String apiSecret;

    public static String apiKey;

    public static String imageUpload;

    @Value("${cloudinary.cloudName}")
    public void setCloudName(String data) {
        cloudName = data;
    }

    @Value("${cloudinary.apiSecret}")
    public void setApiSecret(String data) {
        apiSecret = data;
    }

    @Value("${cloudinary.apiKey}")
    public void setApiKey(String data) {
        apiKey = data;
    }

    @Value("${cloudinary.imageUpload}")
    public void setImageUpload(String data) {
        imageUpload = data;
    }

    public CustomCloudinary() {

    }


}
