package com.sec.gen.next.backend.internal.common.address.validator;

import com.sec.gen.next.backend.internal.api.exception.Error;
import com.sec.gen.next.backend.internal.api.exception.ServiceException;
import com.sec.gen.next.backend.internal.api.external.AddressModel;
import com.sec.gen.next.backend.internal.common.Validator;

import static java.util.Objects.isNull;

public class AddressValidator implements Validator<AddressModel> {

    @Override
    public void validate(AddressModel addressModel) {
        if(isNull(addressModel.getCity()) ||
                isNull(addressModel.getPostalCode()) ||
                        isNull(addressModel.getStreetName())) {
            throw new ServiceException(Error.INVALID_ADDRESS_DATA);
        }
    }
}
