package com.sec.gen.next.backend.api.external;

import com.next.gen.api.Product;
import com.next.gen.sec.model.RegistrationSource;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder(toBuilder = true)
public class UserModel {
    private Integer id;
    private String email;
    private String password;
    private RegistrationSource source;
    private String passwordChange;
    private String phoneNumber;
    private String name;
    private String surname;
    private AddressModel address;
}
