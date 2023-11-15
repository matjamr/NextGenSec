package com.sec.gen.next.backend.product.builder;

import com.sec.gen.next.backend.api.internal.Product;
import com.sec.gen.next.backend.product.ProductContext;
import com.sec.gen.next.backend.product.mapper.ProductMapper;
import com.sec.gen.next.backend.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
public class ProductToDbBuilder implements Consumer<ProductContext> {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public void accept(ProductContext productContext) {
        Product product = productMapper.from(productContext.getProductModel());
        product.setImages(productContext.getImages());

        log.info("Saving product: " + product);
        product = productRepository.save(product);

        productContext.setProduct(product);
    }

}
