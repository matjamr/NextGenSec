package com.sec.gen.next.backend.product.builder;

import com.sec.gen.next.backend.api.external.ProductModel;
import com.sec.gen.next.backend.product.ProductContext;
import com.sec.gen.next.backend.product.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Function;

@RequiredArgsConstructor
public class DefaultProductResultBuilder implements Function<ProductContext, List<ProductModel>> {
    private final ProductMapper productMapper;

    @Override
    public List<ProductModel> apply(ProductContext productContext) {
        var a = productContext.getProductList().get(0).getImages();
        return productContext.getProductList().stream()
                .map(productMapper::from)
//                .map(product -> product.toBuilder().imgUrls(productContext.getImagesId()).build())
                .toList();
    }
}
