package com.sec.gen.next.backend.places.validator;

import com.sec.gen.next.backend.api.exception.Error;
import com.sec.gen.next.backend.api.exception.ServiceException;
import com.sec.gen.next.backend.api.external.AddressModel;
import com.sec.gen.next.backend.api.external.PlacesModel;
import com.sec.gen.next.backend.places.PlacesContext;
import com.sec.gen.next.backend.common.Validator;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class PlaceAddressValidator implements Validator<PlacesContext> {

    private final Validator<AddressModel> addressableValidator;

    @Override
    public void validate(PlacesContext context) {
        final AddressModel addressModel = Optional.of(context)
                .map(PlacesContext::getPlacesModel)
                .map(PlacesModel::getAddressModel)
                .orElseThrow(() -> new ServiceException(Error.INVALID_PLACE_DATA));

        addressableValidator.validate(addressModel);
    }
}
