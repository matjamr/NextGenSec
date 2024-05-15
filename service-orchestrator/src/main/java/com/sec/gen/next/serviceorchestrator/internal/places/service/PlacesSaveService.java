package com.sec.gen.next.serviceorchestrator.internal.places.service;

import com.next.gen.api.Address;
import com.next.gen.api.Places;
import com.next.gen.sec.model.PlacesModel;
import com.sec.gen.next.serviceorchestrator.common.templates.SaveService;
import com.sec.gen.next.serviceorchestrator.external.nominatim.NominatimClient;
import com.sec.gen.next.serviceorchestrator.internal.places.mapper.PlacesMapper;
import com.sec.gen.next.serviceorchestrator.internal.places.repository.PlacesRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.function.Function;

import static com.sec.gen.next.serviceorchestrator.exception.Error.INVALID_ADDRESS_DATA;
import static com.sec.gen.next.serviceorchestrator.exception.Error.INVALID_PLACE_DATA;

@RequiredArgsConstructor
public class PlacesSaveService implements SaveService<PlacesModel, PlacesModel> {

    private final PlacesRepository placesRepository;
    private final PlacesMapper placesMapper;
    private final NominatimClient nominatimClient;
    private final Function<Address, String> nominatimQueryBuilder;

    @Override
    public PlacesModel save(PlacesModel placesModel) {
        return Optional.of(placesModel)
                .map(placesMapper::map)
                .map(this::additionallyMapAddress)
                .map(placesRepository::save)
                .map(placesMapper::map)
                .orElseThrow(INVALID_PLACE_DATA::getError);
    }

    private Places additionallyMapAddress(Places places) {
        if(places.getAddress() != null) {
            var response = nominatimClient.getPlaceCoords(nominatimQueryBuilder.apply(places.getAddress()))
                    .stream()
                    .findFirst()
                    .orElseThrow(INVALID_ADDRESS_DATA::getError);

            places.getAddress().setLatitude(response.getLat()).setLongitude(response.getLon());
        }

        return places;
    }
}
