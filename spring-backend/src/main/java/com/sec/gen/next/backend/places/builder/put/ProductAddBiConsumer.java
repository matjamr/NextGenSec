package com.sec.gen.next.backend.places.builder.put;

import com.sec.gen.next.backend.api.exception.Error;
import com.sec.gen.next.backend.api.exception.ServiceException;
import com.sec.gen.next.backend.api.external.PlacesModel;
import com.sec.gen.next.backend.api.external.UserPlaceAssignmentModel;
import com.sec.gen.next.backend.api.internal.Places;
import com.sec.gen.next.backend.api.internal.Product;
import com.sec.gen.next.backend.places.builder.PlacesMapper;
import com.sec.gen.next.backend.places.repository.PlacesRepository;
import com.sec.gen.next.backend.product.repository.ProductRepository;
import com.sec.gen.next.backend.user.repository.UserPlaceAssignmentRepository;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

@RequiredArgsConstructor
public class ProductAddBiConsumer implements BiConsumer<Places, PlacesModel> {

    private final ProductRepository productRepository;
    private final UserPlaceAssignmentRepository userPlaceAssignmentRepository;

    private void addProduct(UserPlaceAssignmentModel.ProductAdd productAdd, Places places) {
        Optional.ofNullable(productAdd)
                .map(UserPlaceAssignmentModel.ProductAdd::products)
                .ifPresent(products -> {
                    var ret = products.stream()
                            .map(productModel -> productRepository.findById(productModel.getId())
                                    .orElseThrow(() -> new ServiceException(Error.INVALID_PLACE_DATA)))
                            .toList();

                    places.getAuthorizedUsers()
                            .stream()
                            .filter(user -> user.getUser().getEmail().equals(productAdd.user().getEmail()))
                            .forEach(user -> {
                                user.getProducts().addAll(ret);
                                userPlaceAssignmentRepository.save(user);
                            });
                });
    }

    @Override
    public void accept(Places places, PlacesModel placesModel) {
        Optional.ofNullable(placesModel)
                .map(PlacesModel::getAuthorizedUsers)
                .orElse(Collections.emptyList()).stream()
                .map(UserPlaceAssignmentModel::getProductAdd)
                .forEach(productAdd -> addProduct(productAdd, places));
    }
}
