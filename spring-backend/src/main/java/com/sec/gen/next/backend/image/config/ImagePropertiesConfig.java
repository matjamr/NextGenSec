package com.sec.gen.next.backend.image.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "image")
public class ImagePropertiesConfig {
    private String urlTemplate;
}
