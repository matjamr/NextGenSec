package com.sec.gen.next.serviceorchestrator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ServiceOrchestratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceOrchestratorApplication.class, args);
    }

}
