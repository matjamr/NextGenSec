package com.sec.gen.next.backend.places.builder.add;

import com.sec.gen.next.backend.api.external.AddressModel;
import com.sec.gen.next.backend.api.internal.Places;
import com.sec.gen.next.backend.places.PlacesContext;
import com.sec.gen.next.backend.places.builder.PlacesMapper;
import com.sec.gen.next.backend.places.repository.PlacesRepository;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class PlacesToDbBuilder implements Consumer<PlacesContext> {

    private final PlacesRepository placesRepository;
    private final Consumer<AddressModel> addressToDbConsumer;

    @Override
    public void accept(PlacesContext placesContext) {
        addressToDbConsumer.accept(placesContext.getPlacesModel().getAddressModel());

        Places places = PlacesMapper.INSTANCE.from(placesContext.getPlacesModel());
        places = placesRepository.save(places);
        placesContext.setPlacesModel(PlacesMapper.INSTANCE.from(places));
    }
}
