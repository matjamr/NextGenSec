package com.sec.gen.next.backend.places.builder.put;

import com.sec.gen.next.backend.api.external.PlacesModel;
import com.next.gen.api.Places;

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
