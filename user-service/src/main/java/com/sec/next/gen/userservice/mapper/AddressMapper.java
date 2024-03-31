package com.sec.next.gen.userservice.mapper;

import com.next.gen.sec.model.AddressModel;
import com.sec.next.gen.userservice.api.Address;
import org.mapstruct.Mapper;

@Mapper
public interface AddressMapper {
    AddressModel map(Address address);
    Address map(AddressModel addressModel);
}
