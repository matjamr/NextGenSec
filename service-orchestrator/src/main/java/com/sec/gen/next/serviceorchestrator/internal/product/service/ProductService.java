package com.sec.gen.next.serviceorchestrator.internal.product.service;

import com.next.gen.api.SensitiveData;
import com.next.gen.api.User;
import com.next.gen.sec.model.DeviceModel;
import com.next.gen.sec.model.PlacesModel;
import com.next.gen.sec.model.ProductModel;
import com.next.gen.sec.model.Role;
import com.sec.gen.next.serviceorchestrator.api.CustomAuthentication;
import com.sec.gen.next.serviceorchestrator.common.templates.CrudService;
import com.sec.gen.next.serviceorchestrator.internal.email.repository.UserRepository;
import com.sec.gen.next.serviceorchestrator.internal.product.repository.ProductRepository;
import com.sec.gen.next.serviceorchestrator.internal.product.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.sec.gen.next.serviceorchestrator.internal.places.service.CrudPlaceService;
import org.springframework.security.core.context.SecurityContextHolder;

import static com.sec.gen.next.serviceorchestrator.exception.Error.*;

@Slf4j
@RequiredArgsConstructor
public class ProductService implements CrudService<ProductModel, ProductModel, String> {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductModel save(ProductModel productModel) {
        return Optional.of(productMapper.map(productModel))
                .map(productRepository::save)
                .map(productMapper::map)
                .orElseThrow(PRODUCT_EXISTS::getError);
    }

    @Override
    public ProductModel delete(ProductModel productModel) {
        return Optional.of(productMapper.map(productModel))
                .map(product -> {
                    productRepository.delete(product);
                    return product;
                })
                .map(productMapper::map)
                .orElseThrow(INVALID_PRODUCT_DATA::getError);
    }

    @Override
    public ProductModel findBy(String s) {
        return productRepository.findById(s)
                .map(productMapper::map)
                .orElseThrow(INVALID_PRODUCT_DATA::getError);
    }
}
