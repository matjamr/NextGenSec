package com.sec.gen.next.serviceorchestrator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
@EntityScan("com.next.gen.api")
public class ServiceOrchestratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceOrchestratorApplication.class, args);
    }

}
