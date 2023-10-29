package com.sec.gen.next.backend.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "security")
@Data
public class SecurityPropertiesConfig {
    private List<String> supportedRegistrationSources;
}
