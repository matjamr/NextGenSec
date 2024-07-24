package com.sec.gen.next.serviceorchestrator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableFeignClients(basePackages = {"com.next.gen.api.security", "com.sec.gen.next.serviceorchestrator"})
@SpringBootApplication
@EntityScan("com.next.gen.api")
@ComponentScan(basePackages = {"com.next.gen.api.security", "com.sec.gen.next.serviceorchestrator"})
public class ServiceOrchestratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceOrchestratorApplication.class, args);
    }

}
