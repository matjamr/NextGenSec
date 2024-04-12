package com.sec.gen.next.serviceorchestrator.internal.places.service;

import com.sec.gen.next.serviceorchestrator.common.templates.SimpleQueryService;
import com.sec.gen.next.serviceorchestrator.internal.places.repository.PlacesRepository;
import com.sec.gen.next.serviceorchestrator.internal.places.mapper.PlacesMapper;
import com.next.gen.sec.model.PlacesModel;
import lombok.RequiredArgsConstructor;

import static com.sec.gen.next.serviceorchestrator.exception.Error.NO_PLACES_ID;

@RequiredArgsConstructor
public class SimpleQueryPlacesService implements SimpleQueryService<String, PlacesModel> {

    private final PlacesRepository placesRepository;
    private final PlacesMapper placesMapper;

    @Override
    public PlacesModel findBy(String id) {
        return placesRepository.findById(id)
                .map(placesMapper::map)
                .orElseThrow(NO_PLACES_ID::getError);
    }
}
