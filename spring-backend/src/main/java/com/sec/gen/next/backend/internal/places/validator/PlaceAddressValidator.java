package com.sec.gen.next.backend.internal.places.validator;

import com.sec.gen.next.backend.internal.api.exception.Error;
import com.sec.gen.next.backend.internal.api.exception.ServiceException;
import com.sec.gen.next.backend.internal.api.external.AddressModel;
import com.sec.gen.next.backend.internal.api.external.PlacesModel;
import com.sec.gen.next.backend.internal.places.PlacesContext;
import com.sec.gen.next.backend.internal.common.Validator;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class PlaceAddressValidator implements Validator<PlacesContext> {

    private final Validator<AddressModel> addressableValidator;

    @Override
    public void validate(PlacesContext context) {
        final AddressModel addressModel = Optional.of(context)
                .map(PlacesContext::getPlacesModel)
                .map(PlacesModel::getAddress)
                .orElseThrow(() -> new ServiceException(Error.INVALID_PLACE_DATA));

        addressableValidator.validate(addressModel);
    }
}
