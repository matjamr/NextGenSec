package com.sec.gen.next.serviceorchestrator.internal.places.service;

import com.next.gen.api.custom.BetterOptional;
import com.next.gen.sec.model.PlacesModel;
import com.sec.gen.next.serviceorchestrator.common.templates.DeleteService;
import com.sec.gen.next.serviceorchestrator.common.templates.SimpleQueryService;
import com.sec.gen.next.serviceorchestrator.internal.places.repository.PlacesRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.sec.gen.next.serviceorchestrator.exception.Error.INVALID_PLACE_DATA;
import static com.sec.gen.next.serviceorchestrator.exception.Error.ONE_OF_PLACES_DO_NOT_EXIST;

@RequiredArgsConstructor
public class PlacesDeleteService implements DeleteService<List<String>, List<String>> {

    private final PlacesRepository placesRepository;

    @Override
    public List<String> delete(List<String> strings) {
        return BetterOptional.of(strings)
                .verify(() -> strings.stream().allMatch(placesRepository::existsById), ONE_OF_PLACES_DO_NOT_EXIST::getError)
                .peek(placesRepository::deleteAllById)
                .orElseThrow(INVALID_PLACE_DATA::getError);
    }
}
