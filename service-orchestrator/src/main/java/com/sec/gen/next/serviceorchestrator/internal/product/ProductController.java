package com.sec.gen.next.serviceorchestrator.internal.product;

import com.next.gen.sec.model.ProductModel;
import com.next.gen.sec.model.SensitiveDataModel;
import com.sec.gen.next.serviceorchestrator.common.templates.CrudService;
import com.sec.gen.next.serviceorchestrator.common.templates.DeleteService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final CrudService<ProductModel, ProductModel, String> productService;
    private final DeleteService<List<ProductModel>, List<ProductModel>> productDeleteService;
    private final CrudService<SensitiveDataModel, SensitiveDataModel, String> userProductQueryingService;

    @Transactional
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
    @PostMapping("/user/retrieve")
    public List<SensitiveDataModel> getProductsForUser() {
        return userProductQueryingService.findAll();
    }

    @Transactional
    @PostMapping("/user")
    public SensitiveDataModel addProductsForUser(@RequestBody SensitiveDataModel sensitiveData) {
        return userProductQueryingService.save(sensitiveData);
    }

    @DeleteMapping("/user")
    public SensitiveDataModel deleteProductsForUser(@RequestBody SensitiveDataModel sensitiveData) {
        return userProductQueryingService.delete(sensitiveData);
    }

    @Transactional
    @DeleteMapping
    public List<ProductModel> deleteProduct(@RequestBody List<ProductModel> productModel) {
        return productDeleteService.delete(productModel);
    }
}
