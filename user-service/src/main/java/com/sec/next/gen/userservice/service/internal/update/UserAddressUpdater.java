package com.sec.next.gen.userservice.service.internal.update;

import com.next.gen.sec.model.AddressModel;
import com.next.gen.sec.model.UserModel;
import com.next.gen.api.Address;
import com.next.gen.api.User;
import com.sec.next.gen.userservice.mapper.AddressMapper;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.function.BiConsumer;

@RequiredArgsConstructor
public class UserAddressUpdater implements BiConsumer<User, UserModel> {

    private final AddressMapper addressMapper;

    @Override
    public void accept(User user, UserModel userModel) {
        Optional.ofNullable(userModel.getAddress())
                .ifPresent(addressModel -> processAddress(user, addressModel));
    }

    private void processAddress(User user, AddressModel addressModel) {
        Optional.ofNullable(user.getAddress())
                .ifPresentOrElse(existingAddress -> this.updateAddress(existingAddress, addressModel),
                        () -> this.createAddress(user, addressModel));
    }

    private void createAddress(User user, AddressModel addressModel) {
        user.setAddress(addressMapper.map(addressModel));
    }

    private void updateAddress(Address existingAddress, AddressModel addressModel) {
        Optional.ofNullable(addressModel.getCity())
                .ifPresent(existingAddress::setCity);

        Optional.ofNullable(addressModel.getPostalCode())
                .ifPresent(existingAddress::setPostalCode);

        Optional.ofNullable(addressModel.getStreetName())
                .ifPresent(existingAddress::setStreetName);
    }
}
