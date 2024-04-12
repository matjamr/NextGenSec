package com.sec.gen.next.serviceorchestrator.internal.places.service;

import com.next.gen.api.custom.BetterOptional;
import com.next.gen.sec.model.PlacesModel;
import com.sec.gen.next.serviceorchestrator.common.templates.CrudService;
import com.sec.gen.next.serviceorchestrator.internal.places.mapper.PlacesMapper;
import com.sec.gen.next.serviceorchestrator.internal.places.repository.PlacesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.sec.gen.next.serviceorchestrator.exception.Error.*;
import static java.util.Objects.isNull;

@Slf4j
@RequiredArgsConstructor
public class CrudPlaceService implements CrudService<PlacesModel, PlacesModel, String> {
    private final PlacesRepository placesRepository;
    private final PlacesMapper placesMapper;

    @Override
    public PlacesModel save(PlacesModel placesModel) {
        return BetterOptional.of(placesModel)
                .verify(() -> isNull(placesModel.getId()), PLACE_EXISTS.getError())
                .optionalMap(placesMapper::map)
                .map(placesRepository::save)
                .map(placesMapper::map)
                .orElseThrow(PLACE_EXISTS::getError);
    }

    @Override
    public List<PlacesModel> findAll() {
        return placesRepository.findAll()
                .stream()
                .map(placesMapper::map)
                .toList();
    }

    @Override
    public PlacesModel delete(PlacesModel placesModel) {
        return BetterOptional.of(placesModel)
                .verify(() -> placesRepository.existsById(placesModel.getId()), NO_PLACES_ID.getError())
                .map(placesMapper::map)
                .peek(placesRepository::delete)
                .map(placesMapper::map)
                .orElseThrow(INVALID_PLACE_DATA::getError);
    }

    @Override
    public PlacesModel findBy(String s) {
        return placesRepository.findById(s)
                .map(placesMapper::map)
                .orElseThrow(NO_PLACES_ID::getError);
    }
}
