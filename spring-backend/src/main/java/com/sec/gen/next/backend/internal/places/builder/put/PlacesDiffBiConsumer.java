package com.sec.gen.next.backend.internal.places.builder.put;

import com.sec.gen.next.backend.internal.api.external.PlacesModel;
import com.sec.gen.next.backend.internal.api.internal.Places;

import java.util.Optional;
import java.util.function.BiConsumer;

public class PlacesDiffBiConsumer implements BiConsumer<Places, PlacesModel> {

    @Override
    public void accept(Places places, PlacesModel placesModel) {
        Optional.ofNullable(placesModel.getEmailPlace())
                .ifPresent(places::setEmailPlace);

        Optional.ofNullable(placesModel.getPlaceName())
                .ifPresent(places::setPlaceName);
    }

}
