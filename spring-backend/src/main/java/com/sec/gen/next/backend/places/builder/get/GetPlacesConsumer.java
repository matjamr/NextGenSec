package com.sec.gen.next.backend.places.builder.get;

import com.sec.gen.next.backend.api.external.PlacesModel;
import com.sec.gen.next.backend.api.internal.Places;
import com.sec.gen.next.backend.places.PlacesContext;
import com.sec.gen.next.backend.places.builder.PlacesMapper;
import com.sec.gen.next.backend.places.repository.PlacesRepository;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class GetPlacesConsumer implements Consumer<PlacesContext> {

    private final PlacesRepository placesRepository;
    private final PlacesMapper placesMapper;

    @Override
    public void accept(PlacesContext placesContext) {
        placesContext.setPlacesModel(PlacesModel.builder()
                        .batchRetrieve(placesMapper.from(placesRepository.findAll()))
                .build());
    }
}
