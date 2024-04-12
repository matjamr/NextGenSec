package com.sec.gen.next.serviceorchestrator.internal.product.config;


import com.next.gen.sec.model.ProductModel;
import com.sec.gen.next.serviceorchestrator.common.templates.CrudService;
import com.sec.gen.next.serviceorchestrator.internal.product.mapper.ProductMapper;
import com.sec.gen.next.serviceorchestrator.internal.product.repository.ProductRepository;
import com.sec.gen.next.serviceorchestrator.internal.product.service.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ProductBeansConfig {

    @Bean
    public CrudService<ProductModel, ProductModel, String> productService(
            final ProductRepository productRepository,
            final ProductMapper productMapper
            ) {
        return new ProductService(productRepository, productMapper);
    }
}
