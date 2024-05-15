package com.sec.gen.next.serviceorchestrator.external;

import com.next.gen.api.Address;
import com.sec.gen.next.serviceorchestrator.external.nominatim.NominatimQueryBuilder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

public class FeignConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new SimpleErrorDecoder();
    }
}
