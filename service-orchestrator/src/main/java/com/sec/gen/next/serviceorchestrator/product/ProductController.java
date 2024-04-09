package com.sec.gen.next.serviceorchestrator.product;

import com.next.gen.sec.model.ProductModel;
import com.sec.gen.next.serviceorchestrator.common.templates.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final CrudService<ProductModel, ProductModel> productService;

    @PostMapping
    public ProductModel addProduct(
            final @RequestBody ProductModel productModel
    ) {
        return productService.save(productModel);
    }

//    @PutMapping
//    public List<ProductModel> updatePlace(
//            final @Qualifier("productContext") ProductContext productContext,
//            final @RequestBody ProductModel productModel
//    ) {
//        return productDispatcher.dispatch(productContext.toBuilder()
//                        .productModel(productModel)
//                        .build(),
//                RoutingEnum.UPDATE);
//    }

    @GetMapping
    public List<ProductModel> getProducts() {
        return productService.findAll();
    }

//    @DeleteMapping
//    public List<ProductModel> deleteProducts(
//            final @Qualifier("productContext") ProductContext productContext,
//            final @RequestBody ProductModel productModel
//    ) {
//        return productDispatcher.dispatch(productContext.toBuilder()
//                        .productModel(productModel)
//                        .build(),
//                RoutingEnum.DELETE);
//    }
}
