package com.sec.gen.next.backend.method.config;

import com.sec.gen.next.backend.image.repository.ImageRepository;
import com.sec.gen.next.backend.method.mapper.MethodMapper;
import com.sec.gen.next.backend.method.repository.MethodRepository;
import com.sec.gen.next.backend.method.service.MethodService;
import com.sec.gen.next.backend.method.service.MethodServiceImpl;
import com.sec.gen.next.backend.product.repository.ProductRepository;
import com.sec.gen.next.backend.user.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MethodConfig {

    @Bean
    public MethodService methodService(
            final UserRepository userRepository,
            final ProductRepository productRepository,
            final MethodMapper methodMapper,
            final ImageRepository imageRepository,
            final MethodRepository methodRepository
            ) {
        return new MethodServiceImpl(
                methodMapper,
                methodRepository,
                userRepository,
                imageRepository,
                productRepository
        );
    }
}
