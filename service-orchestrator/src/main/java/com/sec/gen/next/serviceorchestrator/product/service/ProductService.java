package com.sec.gen.next.serviceorchestrator.product.service;

import com.next.gen.api.Product;
import com.next.gen.sec.model.ProductModel;
import com.sec.gen.next.serviceorchestrator.common.templates.CrudService;
import com.sec.gen.next.serviceorchestrator.exception.ServiceException;
import com.sec.gen.next.serviceorchestrator.product.mapper.ProductMapper;
import com.sec.gen.next.serviceorchestrator.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

import static com.sec.gen.next.serviceorchestrator.exception.Error.INVALID_PRODUCT_DATA;
import static com.sec.gen.next.serviceorchestrator.exception.Error.PRODUCT_EXISTS;

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
    public List<ProductModel> findAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::map)
                .toList();
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
}
