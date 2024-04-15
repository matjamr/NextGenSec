package com.sec.gen.next.serviceorchestrator.external;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class FeignConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new SimpleErrorDecoder();
    }
}