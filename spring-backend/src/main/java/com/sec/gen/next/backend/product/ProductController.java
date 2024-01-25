package com.sec.gen.next.backend.product;

import com.sec.gen.next.backend.api.external.ProductModel;
import com.sec.gen.next.backend.common.Dispatcher;
import com.sec.gen.next.backend.places.builder.RoutingEnum;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final Dispatcher<List<ProductModel>, ProductContext, RoutingEnum> productDispatcher;

    public ProductController(
            @Qualifier("productDispatcher") Dispatcher<List<ProductModel>, ProductContext, RoutingEnum> productDispatcher) {
        this.productDispatcher = productDispatcher;
    }

    @PostMapping
    public List<ProductModel> addProduct(
            final @Qualifier("productContext") ProductContext productContext,
            final @RequestBody ProductModel productModel
    ) {
        return productDispatcher.dispatch(productContext.toBuilder()
                        .productModel(productModel)
                        .build(),
                RoutingEnum.ADD);
    }

    @PutMapping
    public List<ProductModel> updatePlace(
            final @Qualifier("productContext") ProductContext productContext,
            final @RequestBody ProductModel productModel
    ) {
        return productDispatcher.dispatch(productContext.toBuilder()
                        .productModel(productModel)
                        .build(),
                RoutingEnum.UPDATE);
    }

    @GetMapping
    public List<ProductModel> getProducts(
            final @Qualifier("productContext") ProductContext productContext
    ) {
        return productDispatcher.dispatch(productContext, RoutingEnum.LIST_GET);
    }

    @DeleteMapping
    public List<ProductModel> deleteProducts(
            final @Qualifier("productContext") ProductContext productContext,
            final @RequestBody ProductModel productModel
    ) {
        return productDispatcher.dispatch(productContext.toBuilder()
                        .productModel(productModel)
                        .build(),
                RoutingEnum.DELETE);
    }
}
