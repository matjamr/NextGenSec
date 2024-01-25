package com.sec.gen.next.backend.internal.history.config;

import com.sec.gen.next.backend.internal.history.repository.HistoryRepository;
import com.sec.gen.next.backend.internal.history.service.HistoryMapper;
import com.sec.gen.next.backend.internal.history.service.HistoryService;
import com.sec.gen.next.backend.internal.history.service.HistoryServiceImpl;
import com.sec.gen.next.backend.internal.places.repository.PlacesRepository;
import com.sec.gen.next.backend.internal.product.repository.ProductRepository;
import com.sec.gen.next.backend.internal.user.repository.UserRepository;
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
            ProductRepository productRepository
    ) {
        return new HistoryServiceImpl(historyMapper, historyRepository, userRepository, placesRepository, productRepository);
    }
}
