package com.sec.gen.next.serviceorchestrator.internal.places.service.products;

import com.next.gen.api.Places;
import com.next.gen.sec.model.ModifyProductsPlaceModel;
import com.next.gen.sec.model.PlacesModel;
import com.sec.gen.next.serviceorchestrator.common.templates.SaveService;
import com.sec.gen.next.serviceorchestrator.common.templates.UpdateService;
import com.sec.gen.next.serviceorchestrator.exception.ServiceException;
import com.sec.gen.next.serviceorchestrator.internal.places.mapper.PlacesMapper;
import com.sec.gen.next.serviceorchestrator.internal.places.repository.PlacesRepository;
import com.sec.gen.next.serviceorchestrator.internal.product.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.sec.gen.next.serviceorchestrator.exception.Error.NO_PLACES_ID;
import static com.sec.gen.next.serviceorchestrator.exception.Error.PRODUCT_EXISTS;

@Service
@RequiredArgsConstructor
public class AddProductToPlaceService implements UpdateService<ModifyProductsPlaceModel, PlacesModel> {

    private final PlacesRepository placesRepository;
    private final ProductMapper productMapper;
    private final PlacesMapper placesMapper;

    @Override
    public PlacesModel update(ModifyProductsPlaceModel modifyProductsPlaceModel) {

        Places places = placesRepository.findByPlaceName(modifyProductsPlaceModel.getPlaceName())
                .orElseThrow(NO_PLACES_ID::getError);

        modifyProductsPlaceModel.getProducts()
                .stream()
                .filter(product -> places.getProducts().stream()
                        .anyMatch(prod -> product.getName().equals(prod.getName())))
                .findAny()
                .ifPresent(p -> {
                    throw new ServiceException(PRODUCT_EXISTS);
                });

        places.getProducts().addAll(modifyProductsPlaceModel.getProducts().stream()
                .map(productMapper::map)
                .toList());

        placesRepository.save(places);

        return placesMapper.map(places);
    }
}
