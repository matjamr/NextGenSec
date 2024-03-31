package com.sec.gen.next.backend.places.builder.put;

import com.sec.gen.next.backend.api.exception.ServiceException;
import com.sec.gen.next.backend.api.external.PlacesModel;
import com.next.gen.api.Places;
import com.sec.gen.next.backend.places.repository.PlacesRepository;
import com.sec.gen.next.backend.places.PlacesContext;
import com.sec.gen.next.backend.places.builder.PlacesMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.sec.gen.next.backend.api.exception.Error.NO_PLACES_ID;

@RequiredArgsConstructor
public class PlacesUpdater implements Consumer<PlacesContext> {

    private final PlacesRepository placesRepository;
    private final PlacesMapper placesMapper;
    private final List<BiConsumer<Places, PlacesModel>> updaterList;

    @Override
    @Transactional
    public void accept(PlacesContext placesContext) {
        PlacesModel placesModel = placesContext.getPlacesModel();

        Places places = placesRepository.findById(placesModel.getId())
                .orElseThrow(() -> new ServiceException(NO_PLACES_ID));

        updaterList.forEach(updater -> updater.accept(places, placesModel));
        var ret = placesRepository.save(places);
        placesContext.setBatchPlacesModel(List.of(placesMapper.from(ret)));
    }
}
