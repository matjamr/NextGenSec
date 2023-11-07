package com.sec.gen.next.backend.product;

import com.sec.gen.next.backend.api.external.ProductModel;
import com.sec.gen.next.backend.common.Dispatcher;
import com.sec.gen.next.backend.places.builder.RoutingEnum;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final Dispatcher<ProductModel, ProductContext, RoutingEnum> productDispatcher;

    public ProductController(
            @Qualifier("productDispatcher") Dispatcher<ProductModel, ProductContext, RoutingEnum> productDispatcher) {
        this.productDispatcher = productDispatcher;
    }

    @PostMapping
    public ProductModel addProduct(
            final @Qualifier("productContext") ProductContext productContext,
            final @RequestBody ProductModel productModel
    ) {
        return productDispatcher.dispatch(productContext.toBuilder()
                        .productModel(productModel)
                        .build(),
                RoutingEnum.ADD);
    }

    @PutMapping
    public ProductModel updatePlace(
            final @Qualifier("productContext") ProductContext productContext,
            final @RequestBody ProductModel productModel
    ) {
        return productDispatcher.dispatch(productContext.toBuilder()
                        .productModel(productModel)
                        .build(),
                RoutingEnum.UPDATE);
    }

    @GetMapping
    public ProductModel getPlaces(
            final @Qualifier("productContext") ProductContext productContext,
            final @RequestBody ProductModel productModel
    ) {
        return productDispatcher.dispatch(productContext.toBuilder()
                        .productModel(productModel)
                        .build(),
                RoutingEnum.GET);
    }

    @DeleteMapping
    public ProductModel deletePlaces(
            final @Qualifier("productContext") ProductContext productContext,
            final @RequestBody ProductModel productModel
    ) {
        return productDispatcher.dispatch(productContext.toBuilder()
                        .productModel(productModel)
                        .build(),
                RoutingEnum.DELETE);
    }
}
