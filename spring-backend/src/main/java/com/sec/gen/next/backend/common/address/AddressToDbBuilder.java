package com.sec.gen.next.backend.common.address;

import com.sec.gen.next.backend.api.external.AddressModel;
import com.sec.gen.next.backend.api.internal.Address;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class AddressToDbBuilder implements Consumer<AddressModel> {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    @Override
    public void accept(AddressModel addressModel) {
        Address address = addressMapper.from(addressModel);
        address = addressRepository.save(address);
        addressModel = addressMapper.from(address);
    }
}
