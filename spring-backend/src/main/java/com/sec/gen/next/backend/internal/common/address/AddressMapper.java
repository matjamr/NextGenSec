package com.sec.gen.next.backend.internal.common.address;

import com.sec.gen.next.backend.internal.api.external.AddressModel;
import com.sec.gen.next.backend.internal.api.internal.Address;
import org.mapstruct.Mapper;

@Mapper
public interface AddressMapper {
    Address from(AddressModel addressModel);
    AddressModel from(Address addressModel);
}
