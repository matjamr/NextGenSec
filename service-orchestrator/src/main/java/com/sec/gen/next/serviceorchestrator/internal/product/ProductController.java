package com.sec.gen.next.serviceorchestrator.internal.product;

import com.next.gen.sec.model.ProductModel;
import com.sec.gen.next.serviceorchestrator.common.templates.CrudService;
import com.sec.gen.next.serviceorchestrator.common.templates.DeleteService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final CrudService<ProductModel, ProductModel, String> productService;
    private final DeleteService<List<ProductModel>, List<ProductModel>> productDeleteService;

    @PostMapping
    public ProductModel addProduct(
            final @RequestBody ProductModel productModel
    ) {
        return productService.save(productModel);
    }

    @Transactional
    @GetMapping
    public List<ProductModel> getProducts() {
        return productService.findAll();
    }

    @Transactional
    @DeleteMapping
    public List<ProductModel> deleteProduct(@RequestBody List<ProductModel> productModel) {
        return productDeleteService.delete(productModel);
    }
}
