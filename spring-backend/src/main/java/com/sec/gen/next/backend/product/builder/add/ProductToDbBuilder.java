package com.sec.gen.next.backend.product.builder.add;

import com.sec.gen.next.backend.product.ProductContext;
import com.sec.gen.next.backend.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class ProductToDbBuilder implements Consumer<ProductContext> {
    private final ProductRepository productRepository;

    @Override
    public void accept(ProductContext productContext) {

    }

}
