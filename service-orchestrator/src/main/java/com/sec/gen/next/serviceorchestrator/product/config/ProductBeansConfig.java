package com.sec.gen.next.serviceorchestrator.product.config;


import com.next.gen.sec.model.ProductModel;
import com.sec.gen.next.serviceorchestrator.common.templates.CrudService;
import com.sec.gen.next.serviceorchestrator.product.mapper.ProductMapper;
import com.sec.gen.next.serviceorchestrator.product.repository.ProductRepository;
import com.sec.gen.next.serviceorchestrator.product.service.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;



@Configuration
public class ProductBeansConfig {

    @Bean
    public CrudService<ProductModel, ProductModel> productService(
            final ProductRepository productRepository,
            final ProductMapper productMapper
            ) {
        return new ProductService(productRepository, productMapper);
    }
}
