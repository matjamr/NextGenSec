package com.sec.gen.next.backend.history.config;

import com.sec.gen.next.backend.common.kafka.KafkaChatServiceProducer;
import com.sec.gen.next.backend.device.repository.DeviceRepository;
import com.sec.gen.next.backend.history.repository.HistoryRepository;
import com.sec.gen.next.backend.history.service.HistoryMapper;
import com.sec.gen.next.backend.history.service.HistoryService;
import com.sec.gen.next.backend.history.service.HistoryServiceImpl;
import com.sec.gen.next.backend.places.repository.PlacesRepository;
import com.sec.gen.next.backend.product.repository.ProductRepository;
import com.sec.gen.next.backend.user.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HistoryBeansConfig {

    @Bean
    public HistoryService historyService(
            HistoryMapper historyMapper,
            HistoryRepository historyRepository,
            UserRepository userRepository,
            PlacesRepository placesRepository,
            ProductRepository productRepository,
            DeviceRepository deviceRepository,
            KafkaChatServiceProducer kafkaChatServiceProducer
    ) {
        return new HistoryServiceImpl(historyMapper, historyRepository,
                userRepository, placesRepository,
                productRepository ,deviceRepository,
                kafkaChatServiceProducer);
    }
}
