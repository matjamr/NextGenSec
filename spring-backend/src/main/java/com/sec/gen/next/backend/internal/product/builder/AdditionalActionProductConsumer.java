package com.sec.gen.next.backend.internal.product.builder;

import com.sec.gen.next.backend.internal.image.builder.ImageLoader;
import com.sec.gen.next.backend.internal.product.ProductContext;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class AdditionalActionProductConsumer implements Consumer<ProductContext> {
    private final Consumer<ImageLoader> imageLoaderConsumer;

    @Override
    public void accept(ProductContext productContext) {
        imageLoaderConsumer.accept(productContext);
    }
}
