package com.sec.gen.next.serviceorchestrator.internal.places.service;

import com.next.gen.sec.model.PlacesModel;
import com.sec.gen.next.serviceorchestrator.common.templates.SaveService;
import com.sec.gen.next.serviceorchestrator.internal.places.mapper.PlacesMapper;
import com.sec.gen.next.serviceorchestrator.internal.places.repository.PlacesRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.sec.gen.next.serviceorchestrator.exception.Error.INVALID_PLACE_DATA;

@RequiredArgsConstructor
public class PlacesSaveService implements SaveService<PlacesModel, PlacesModel> {

    private final PlacesRepository placesRepository;
    private final PlacesMapper placesMapper;

    @Override
    public PlacesModel save(PlacesModel placesModel) {
        return Optional.of(placesModel)
                .map(placesMapper::map)
                .map(placesRepository::save)
                .map(placesMapper::map)
                .orElseThrow(INVALID_PLACE_DATA::getError);
    }
}
