package com.sec.gen.next.backend.product.config;

import com.sec.gen.next.backend.api.exception.RecoverableServiceException;
import com.sec.gen.next.backend.api.external.ProductModel;
import com.sec.gen.next.backend.common.Dispatcher;
import com.sec.gen.next.backend.common.Service;
import com.sec.gen.next.backend.common.impl.ServiceImpl;
import com.sec.gen.next.backend.image.builder.ImageLoader;
import com.sec.gen.next.backend.places.builder.RoutingEnum;
import com.sec.gen.next.backend.product.ProductContext;
import com.sec.gen.next.backend.product.builder.*;
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
    public Dispatcher<List<ProductModel>, ProductContext, RoutingEnum> productDispatcher(
            @Qualifier("addProductService") Service<List<ProductModel>, ProductContext> addService,
            @Qualifier("updateProductService") Service<List<ProductModel>, ProductContext> updateService,
            @Qualifier("getProductsService") Service<List<ProductModel>, ProductContext> getProductsService,
            @Qualifier("deleteProductService") Service<List<ProductModel>, ProductContext> deleteService
    ) {
        return new ProductDispatcher(Map.of(
                ADD, addService,
                UPDATE, updateService,
                LIST_GET, getProductsService,
                DELETE, deleteService
        ));
    }

    @Bean("addProductService")
    public Service<List<ProductModel>, ProductContext> addProductService(
            @Qualifier("addProductFlow") List<Consumer<ProductContext>> addProductFlow,
            @Qualifier("defaultProductsResultBuilder") Function<ProductContext, List<ProductModel>> defaultProductResultBuilder,
            @Qualifier("recoverableProductActionConsumer") BiConsumer<ProductContext, RecoverableServiceException> recoverableActionConsumer
    ) {
        return new ServiceImpl<>(List.of(),
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
                additionalActionsConsumer,
                productToDbBuilder
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

    @Bean("productReaderConsumer")
    public Consumer<ProductContext> productReaderConsumer(
            final ProductRepository productRepository
    ) {
        return new ProductReaderConsumer(productRepository);
    }

    @Bean("updateProductService")
    public Service<List<ProductModel>, ProductContext> updateProductService(
            @Qualifier("defaultProductsResultBuilder") Function<ProductContext, List<ProductModel>> defaultProductResultBuilder,
            @Qualifier("recoverableProductActionConsumer") BiConsumer<ProductContext, RecoverableServiceException> recoverableActionConsumer
    ) {
        return new ServiceImpl<>(List.of(), List.of(), defaultProductResultBuilder, recoverableActionConsumer);
    }

    @Bean("deleteProductService")
    public Service<List<ProductModel>, ProductContext> deleteProductService(
            @Qualifier("defaultProductsResultBuilder") Function<ProductContext, List<ProductModel>> defaultProductResultBuilder,
            @Qualifier("recoverableProductActionConsumer") BiConsumer<ProductContext, RecoverableServiceException> recoverableActionConsumer
    ) {
        return new ServiceImpl<>(List.of(), List.of(), defaultProductResultBuilder, recoverableActionConsumer);
    }

    @Bean("getProductsService")
    public Service<List<ProductModel>, ProductContext> getProductsService(
            @Qualifier("defaultProductsResultBuilder") Function<ProductContext, List<ProductModel>> defaultProductsResultBuilder,
            @Qualifier("recoverableProductActionConsumer") BiConsumer<ProductContext, RecoverableServiceException> recoverableActionConsumer,
            @Qualifier("getProductsFlow") List<Consumer<ProductContext>> getProductsFlow
    ) {
        return new ServiceImpl<>(List.of(), getProductsFlow, defaultProductsResultBuilder, recoverableActionConsumer);
    }

    @Bean("getProductsFlow")
    public List<Consumer<ProductContext>> getProductsFlow(
            @Qualifier("productReaderConsumer") Consumer<ProductContext> productReaderConsumer
    ) {
        return List.of(
                productReaderConsumer
        );
    }

    @Bean("recoverableProductActionConsumer")
    public BiConsumer<ProductContext, RecoverableServiceException> recoverableActionConsumer() {
        return (context, error) -> context.getErrors().add(error.getError());
    }

    @Bean("defaultProductsResultBuilder")
    public Function<ProductContext, List<ProductModel>> defaultProductResultBuilder(
            final ProductMapper productMapper
    ) {
        return new DefaultProductResultBuilder(productMapper);
    }
}
