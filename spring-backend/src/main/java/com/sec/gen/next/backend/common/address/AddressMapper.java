package com.sec.gen.next.backend.common.address;

import com.sec.gen.next.backend.api.external.AddressModel;
import com.sec.gen.next.backend.api.internal.Address;
import org.mapstruct.Mapper;

@Mapper
public interface AddressMapper {
    Address from(AddressModel addressModel);
    AddressModel from(Address addressModel);
}
