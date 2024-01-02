package com.sec.gen.next.backend.places.builder.add;

import com.sec.gen.next.backend.api.external.UserPlaceAssignmentModel;
import com.sec.gen.next.backend.api.internal.Places;
import com.sec.gen.next.backend.api.internal.UserPlaceAssignment;
import com.sec.gen.next.backend.places.PlacesContext;
import com.sec.gen.next.backend.places.builder.PlacesMapper;
import com.sec.gen.next.backend.places.repository.PlacesRepository;
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
        placesContext.setPlacesModel(placesMapper.from(places));
    }
}
