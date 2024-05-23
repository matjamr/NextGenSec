package com.sec.gen.next.serviceorchestrator.internal.product.service;

import com.next.gen.api.SensitiveData;
import com.next.gen.api.User;
import com.next.gen.sec.model.DeviceModel;
import com.next.gen.sec.model.PlacesModel;
import com.next.gen.sec.model.ProductModel;
import com.next.gen.sec.model.Role;
import com.sec.gen.next.serviceorchestrator.api.CustomAuthentication;
import com.sec.gen.next.serviceorchestrator.common.templates.CrudService;
import com.sec.gen.next.serviceorchestrator.internal.email.repository.UserRepository;
import com.sec.gen.next.serviceorchestrator.internal.product.mapper.ProductMapper;
import com.sec.gen.next.serviceorchestrator.internal.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import com.sec.gen.next.serviceorchestrator.common.templates.QueryService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.sec.gen.next.serviceorchestrator.exception.Error.INVALID_USER_DATA;

//@Component
@RequiredArgsConstructor
public class RetrieveProductsService implements QueryService<ProductModel, String> {
    private final CrudService<PlacesModel, PlacesModel, String> crudPlaceService;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductModel> findAll() {
        CustomAuthentication user = (CustomAuthentication) SecurityContextHolder.getContext().getAuthentication();
        var products = productRepository.findAll();

        if(user == null || user.getRole() == null || user.getRole().equals(Role.SYSTEM)) {
            return products.stream().map(productMapper::map).toList();
        }

        var userDbData = userRepository.findByEmail(user.getEmail()).orElseThrow(INVALID_USER_DATA::getError);

        if(user.getRole().equals(Role.USER)) {
            return Optional.of(userDbData)
                    .map(User::getSensitiveData)
                    .orElse(Collections.emptyList())
                    .stream()
                    .map(SensitiveData::getProduct)
                    .map(productMapper::map)
                    .toList();
        }

        return crudPlaceService.findAll()
                .stream()
                .findFirst()
                .map(PlacesModel::getProducts)
                .orElse(Collections.emptyList());

    }
}
