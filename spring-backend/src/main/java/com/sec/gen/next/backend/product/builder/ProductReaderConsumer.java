package com.sec.gen.next.backend.product.builder;

import com.sec.gen.next.backend.product.ProductContext;
import com.sec.gen.next.backend.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class ProductReaderConsumer implements Consumer<ProductContext> {
    private final ProductRepository productRepository;

    @Override
    public void accept(ProductContext productContext) {
        productContext.setProductList(productRepository.findAll());
    }
}
