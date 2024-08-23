package com.sec.gen.next.serviceorchestrator.external;

import com.next.gen.api.Address;
import com.sec.gen.next.serviceorchestrator.external.nominatim.NominatimQueryBuilder;
import feign.Client;
import feign.Logger;
import feign.codec.ErrorDecoder;
import feign.httpclient.ApacheHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class FeignConfig {

    @Bean
    public Client feignClient() {
        return new ApacheHttpClient(HttpClientBuilder.create()
                .setRedirectStrategy(new LaxRedirectStrategy())
                .build());
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}

