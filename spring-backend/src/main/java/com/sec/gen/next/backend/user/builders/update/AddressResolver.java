package com.sec.gen.next.backend.user.builders.update;

import com.sec.gen.next.backend.api.exception.Error;
import com.sec.gen.next.backend.api.exception.ServiceException;
import com.sec.gen.next.backend.api.external.AdditionalInformationUpdateModel;
import com.sec.gen.next.backend.api.external.AddressModel;
import com.sec.gen.next.backend.api.external.UserModel;
import com.sec.gen.next.backend.api.internal.Address;
import com.sec.gen.next.backend.api.internal.User;
import com.sec.gen.next.backend.common.address.AddressMapper;
import com.sec.gen.next.backend.common.address.AddressRepository;
import com.sec.gen.next.backend.user.service.Executor;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class AddressResolver implements Executor<User, UserModel> {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    @Override
    public boolean shouldAccept(User user, UserModel userModel) {
        return Optional.ofNullable(userModel)
                .map(UserModel::getAddress)
                .isPresent();
    }

    @Override
    public void accept(User user, UserModel userModel) {
        if(Optional.ofNullable(user.getAddress()).map(Address::getId).isEmpty()) {
            addAddress(user, userModel.getAddress());
        } else {
            updateAddress(user, userModel.getAddress());
        }
    }

    private void updateAddress(User user, AddressModel addressModel) {
        Address address = addressRepository.findById(addressModel.getId())
                .orElseThrow(() -> new ServiceException(Error.INVALID_ADDRESS_DATA));

        user.setAddress(address.setCity(addressModel.getCity())
                .setPostalCode(addressModel.getPostalCode())
                .setStreetName(addressModel.getStreetName()));
    }

    private void addAddress(User user, AddressModel addressModel) {
        Address address = addressRepository.save(addressMapper.from(addressModel));
        user.setAddress(address);
    }
}
