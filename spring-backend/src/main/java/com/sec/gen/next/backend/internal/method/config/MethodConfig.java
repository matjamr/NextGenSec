package com.sec.gen.next.backend.internal.method.config;

import com.sec.gen.next.backend.internal.image.repository.ImageRepository;
import com.sec.gen.next.backend.internal.method.mapper.MethodMapper;
import com.sec.gen.next.backend.internal.method.repository.MethodRepository;
import com.sec.gen.next.backend.internal.method.service.MethodService;
import com.sec.gen.next.backend.internal.method.service.MethodServiceImpl;
import com.sec.gen.next.backend.internal.product.repository.ProductRepository;
import com.sec.gen.next.backend.internal.user.repository.UserRepository;
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
