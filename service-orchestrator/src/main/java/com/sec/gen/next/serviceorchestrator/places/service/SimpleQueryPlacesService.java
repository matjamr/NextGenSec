package com.sec.gen.next.serviceorchestrator.places.service;

import com.next.gen.api.Places;
import com.sec.gen.next.serviceorchestrator.common.templates.SimpleQueryService;
import com.sec.gen.next.serviceorchestrator.places.mapper.PlacesMapper;
import com.sec.gen.next.serviceorchestrator.places.repository.PlacesRepository;
import com.next.gen.sec.model.PlacesModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
