package com.sec.gen.next.backend.product.config;

import com.sec.gen.next.backend.api.exception.RecoverableServiceException;
import com.sec.gen.next.backend.api.external.ProductModel;
import com.sec.gen.next.backend.common.Dispatcher;
import com.sec.gen.next.backend.common.Service;
import com.sec.gen.next.backend.common.impl.SingleEntityService;
import com.sec.gen.next.backend.image.builder.ImageLoader;
import com.sec.gen.next.backend.places.builder.RoutingEnum;
import com.sec.gen.next.backend.product.ProductContext;
import com.sec.gen.next.backend.product.builder.AdditionalActionProductConsumer;
import com.sec.gen.next.backend.product.builder.DefaultProductResultBuilder;
import com.sec.gen.next.backend.product.builder.ProductDispatcher;
import com.sec.gen.next.backend.product.builder.ProductToDbBuilder;
import com.sec.gen.next.backend.product.mapper.ProductMapper;
import com.sec.gen.next.backend.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.sec.gen.next.backend.places.builder.RoutingEnum.*;


@Configuration
public class ProductBeansConfig {
    @Bean("productDispatcher")
    public Dispatcher<ProductModel, ProductContext, RoutingEnum> productDispatcher(
            @Qualifier("addProductService") Service<ProductModel, ProductContext> addService,
            @Qualifier("updateProductService") Service<ProductModel, ProductContext> updateService,
            @Qualifier("getProductService") Service<ProductModel, ProductContext> getService,
            @Qualifier("deleteProductService") Service<ProductModel, ProductContext> deleteService
    ) {
        return new ProductDispatcher(Map.of(
                ADD, addService,
                UPDATE, updateService,
                GET, getService,
                DELETE, deleteService
        ));
    }

    @Bean("addProductService")
    public Service<ProductModel, ProductContext> addProductService(
            @Qualifier("addProductFlow") List<Consumer<ProductContext>> addProductFlow,
            @Qualifier("defaultProductResultBuilder") Function<ProductContext, ProductModel> defaultProductResultBuilder,
            @Qualifier("recoverableProductActionConsumer") BiConsumer<ProductContext, RecoverableServiceException> recoverableActionConsumer
    ) {
        return new SingleEntityService<>(List.of(),
                addProductFlow,
                defaultProductResultBuilder,
                recoverableActionConsumer);
    }

    @Bean("addProductFlow")
    public List<Consumer<ProductContext>> addProductFlow(
            @Qualifier("productToDbBuilder") Consumer<ProductContext> productToDbBuilder,
            @Qualifier("additionalActionProductConsumer") Consumer<ProductContext> additionalActionsConsumer
    ) {
        return List.of(
                productToDbBuilder,
                additionalActionsConsumer
        );
    }

    @Bean("productToDbBuilder")
    public Consumer<ProductContext> productToDbBuilder(
            final ProductRepository productRepository,
            final ProductMapper productMapper
            ) {
        return new ProductToDbBuilder(productRepository, productMapper);
    }

    @Bean("additionalActionProductConsumer")
    public Consumer<ProductContext> additionalActionProductConsumer(
            final @Qualifier("imageLoadConsumer") Consumer<ImageLoader> imageLoaderConsumer
    ) {
        return new AdditionalActionProductConsumer(imageLoaderConsumer);
    }

    @Bean("updateProductService")
    public Service<ProductModel, ProductContext> updateProductService(
            @Qualifier("defaultProductResultBuilder") Function<ProductContext, ProductModel> defaultProductResultBuilder,
            @Qualifier("recoverableProductActionConsumer") BiConsumer<ProductContext, RecoverableServiceException> recoverableActionConsumer
    ) {
        return new SingleEntityService<>(List.of(), List.of(), defaultProductResultBuilder, recoverableActionConsumer);
    }

    @Bean("deleteProductService")
    public Service<ProductModel, ProductContext> deleteProductService(
            @Qualifier("defaultProductResultBuilder") Function<ProductContext, ProductModel> defaultProductResultBuilder,
            @Qualifier("recoverableProductActionConsumer") BiConsumer<ProductContext, RecoverableServiceException> recoverableActionConsumer
    ) {
        return new SingleEntityService<>(List.of(), List.of(), defaultProductResultBuilder, recoverableActionConsumer);
    }

    @Bean("getProductService")
    public Service<ProductModel, ProductContext> getProductService(
            @Qualifier("defaultProductResultBuilder") Function<ProductContext, ProductModel> defaultProductResultBuilder,
            @Qualifier("recoverableProductActionConsumer") BiConsumer<ProductContext, RecoverableServiceException> recoverableActionConsumer
    ) {
        return new SingleEntityService<>(List.of(), List.of(), defaultProductResultBuilder, recoverableActionConsumer);
    }

    @Bean("recoverableProductActionConsumer")
    public BiConsumer<ProductContext, RecoverableServiceException> recoverableActionConsumer() {
        return (context, error) -> context.getErrors().add(error.getError());
    }

    @Bean("defaultProductResultBuilder")
    public Function<ProductContext, ProductModel> defaultProductResultBuilder(
            final ProductMapper productMapper
    ) {
        return new DefaultProductResultBuilder(productMapper);
    }
}
