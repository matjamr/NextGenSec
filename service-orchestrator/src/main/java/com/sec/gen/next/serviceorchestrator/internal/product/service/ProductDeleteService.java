package com.sec.gen.next.serviceorchestrator.internal.product.service;

import com.next.gen.api.custom.BetterOptional;
import com.next.gen.sec.model.ProductModel;
import com.sec.gen.next.serviceorchestrator.common.templates.DeleteService;
import com.sec.gen.next.serviceorchestrator.internal.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.sec.gen.next.serviceorchestrator.exception.Error.INVALID_PRODUCT_DATA;

@RequiredArgsConstructor
public class ProductDeleteService implements DeleteService<List<ProductModel>, List<ProductModel>> {

    private final ProductRepository productRepository;

    @Override
    public List<ProductModel> delete(List<ProductModel> productModels) {
        return BetterOptional.of(productModels)
                .verify(() -> productModels.stream().allMatch(item -> productRepository.existsById(item.getId())), INVALID_PRODUCT_DATA::getError)
                .peek((models) -> models.stream().map(ProductModel::getId).forEach(productRepository::deleteById))
                .orElseThrow(INVALID_PRODUCT_DATA::getError);
    }
}
