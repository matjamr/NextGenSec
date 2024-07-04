package com.sec.gen.next.serviceorchestrator.internal.product.service;

import com.next.gen.api.custom.BetterOptional;
import com.next.gen.sec.model.ProductModel;
import com.sec.gen.next.serviceorchestrator.common.templates.DeleteService;
import com.sec.gen.next.serviceorchestrator.internal.email.repository.UserRepository;
import com.sec.gen.next.serviceorchestrator.internal.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static com.sec.gen.next.serviceorchestrator.exception.Error.INVALID_PRODUCT_DATA;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
public class ProductDeleteService implements DeleteService<List<ProductModel>, List<ProductModel>> {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    // TODO dispatch email to users which methods were for them deleted
    @Override
    public List<ProductModel> delete(List<ProductModel> productModels) {
        return BetterOptional.of(productModels)
                .verify(() -> productModels.stream().allMatch(item -> productRepository.existsById(item.getId())), INVALID_PRODUCT_DATA::getError)
                .peek((models) -> models.forEach(product -> {
                    userRepository.findAll()
                            .stream()
                            .filter(user -> !CollectionUtils.isEmpty(user.getSensitiveData()))
                            .forEach(user -> {
                                        user.getSensitiveData().removeAll(user.getSensitiveData().stream()
                                                .filter(sd -> nonNull(sd.getProduct()))
                                                .filter(sd -> sd.getProduct().getId().equals(product.getId()))
                                                .toList());
                                        userRepository.save(user);
                                    }
                            );


                    productRepository.deleteById(product.getId());
                }))
                .orElseThrow(INVALID_PRODUCT_DATA::getError);
    }
}
