package com.sec.gen.next.backend.product.builder;

import com.sec.gen.next.backend.api.external.ProductModel;
import com.sec.gen.next.backend.product.ProductContext;
import com.sec.gen.next.backend.product.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@RequiredArgsConstructor
public class DefaultProductResultBuilder implements Function<ProductContext, ProductModel> {
    private final ProductMapper productMapper;

    @Override
    public ProductModel apply(ProductContext productContext) {
        return productMapper.from(productContext.getProduct())
                .toBuilder()
                .imgUrls(productContext.getImagesId())
                .build();
    }
}
