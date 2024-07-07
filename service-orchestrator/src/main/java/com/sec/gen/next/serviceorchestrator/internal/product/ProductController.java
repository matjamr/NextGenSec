package com.sec.gen.next.serviceorchestrator.internal.product;

import com.next.gen.sec.model.ProductModel;
import com.next.gen.sec.model.SensitiveDataModel;
import com.sec.gen.next.serviceorchestrator.common.templates.ConditionalListQueryService;
import com.sec.gen.next.serviceorchestrator.common.templates.CrudService;
import com.sec.gen.next.serviceorchestrator.common.templates.DeleteService;
import com.sec.gen.next.serviceorchestrator.common.templates.ModifyService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final CrudService<ProductModel, ProductModel, String> productService;
    private final DeleteService<List<ProductModel>, List<ProductModel>> productDeleteService;
    private final CrudService<SensitiveDataModel, SensitiveDataModel, String> userProductQueryingService;
    private final ConditionalListQueryService<SensitiveDataModel, String> conditionalListQueryService;
    private final ModifyService<SensitiveDataModel> modifySensitiveDataService;

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
    @GetMapping("/user")
    public List<SensitiveDataModel> getProductsForUser(@RequestParam("email") String userEmail) {
        return conditionalListQueryService.findAll(userEmail);
    }

    @Transactional
    @PostMapping("/user")
    public SensitiveDataModel addProductsForUser(@RequestBody SensitiveDataModel sensitiveData) {
        return userProductQueryingService.save(sensitiveData);
    }

    @Transactional
    @PutMapping("/user")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void modifySensitiveData(@RequestBody SensitiveDataModel sensitiveData) {
        modifySensitiveDataService.update(sensitiveData);
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
