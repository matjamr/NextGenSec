package com.sec.gen.next.serviceorchestrator.internal.product;

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
    private final CrudService<ProductModel, ProductModel, String> productService;

    @PostMapping
    public ProductModel addProduct(
            final @RequestBody ProductModel productModel
    ) {
        return productService.save(productModel);
    }

    @GetMapping
    public List<ProductModel> getProducts() {
        return productService.findAll();
    }

    @DeleteMapping
    public ProductModel deleteProduct(@RequestBody ProductModel productModel) {
        return productService.delete(productModel);
    }
}
