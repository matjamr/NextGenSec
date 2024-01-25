package com.sec.gen.next.backend.internal.places.validator;

import com.sec.gen.next.backend.internal.api.exception.Error;
import com.sec.gen.next.backend.internal.api.exception.ServiceException;
import com.sec.gen.next.backend.internal.api.external.PlacesModel;
import com.sec.gen.next.backend.internal.places.repository.PlacesRepository;
import com.sec.gen.next.backend.internal.places.PlacesContext;
import com.sec.gen.next.backend.internal.common.Validator;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class PlaceExistenceValidator implements Validator<PlacesContext> {

    private final PlacesRepository placesRepository;

    @Override
    public void validate(PlacesContext context) {
        final PlacesModel placesModel = Optional.ofNullable(context)
                .map(PlacesContext::getPlacesModel)
                .orElseThrow(() -> new ServiceException(Error.INVALID_PLACE_DATA));

        if(placesRepository.existsByPlaceNameOrEmailPlace(placesModel.getPlaceName(), placesModel.getEmailPlace())) {
            throw new ServiceException(Error.INVALID_PLACE_DATA);
        }
    }
}
