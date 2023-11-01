package com.sec.gen.next.backend.common.address;

import com.sec.gen.next.backend.api.external.AddressModel;
import com.sec.gen.next.backend.api.internal.Address;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class AddressToDbBuilder implements Consumer<AddressModel> {

    private final AddressRepository addressRepository;

    @Override
    public void accept(AddressModel addressModel) {
        Address address = AddressMapper.INSTANCE.from(addressModel);
        address = addressRepository.save(address);
        addressModel = AddressMapper.INSTANCE.from(address);
    }
}
