package com.sec.gen.next.serviceorchestrator.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "security")
@Data
public class SecurityPropertiesConfig {
    private List<String> supportedRegistrationSources;
    private List<PathMethodPair> paths;

    public static class PathMethodPair {
        private String url;
        private String method;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }
    }
}
