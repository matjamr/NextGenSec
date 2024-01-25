package com.sec.gen.next.backend.internal.product.builder;

import com.sec.gen.next.backend.internal.product.repository.ProductRepository;
import com.sec.gen.next.backend.internal.product.ProductContext;
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
