package com.sec.gen.next.backend.internal.places.builder.add;

import com.sec.gen.next.backend.internal.api.external.UserPlaceAssignmentModel;
import com.sec.gen.next.backend.internal.api.internal.Places;
import com.sec.gen.next.backend.internal.api.internal.UserPlaceAssignment;
import com.sec.gen.next.backend.internal.places.repository.PlacesRepository;
import com.sec.gen.next.backend.internal.places.PlacesContext;
import com.sec.gen.next.backend.internal.places.builder.PlacesMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@RequiredArgsConstructor
public class PlacesToDbBuilder implements Consumer<PlacesContext> {

    private final PlacesRepository placesRepository;
    private final Function<List<UserPlaceAssignmentModel>, List<UserPlaceAssignment>> userPlaceAssignmentToDb;
    private final Consumer<PlacesContext> dynamicUpdater;
    private final PlacesMapper placesMapper;

    @Override
    public void accept(PlacesContext placesContext) {
        dynamicUpdater.accept(placesContext);
        userPlaceAssignmentToDb.apply(placesContext.getPlacesModel().getAuthorizedUsers());

        Places places = placesMapper.from(placesContext.getPlacesModel());

        places = placesRepository.save(places);
        placesContext.setBatchPlacesModel(List.of(placesMapper.from(places)));
    }
}
