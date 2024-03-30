package com.sec.gen.next.backend.api.external;

import com.sec.gen.next.backend.api.internal.Product;
import com.sec.gen.next.backend.api.internal.RegisterSource;
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
    private RegisterSource source;
    private String passwordChange;
    private String phoneNumber;
    private String name;
    private String surname;
    private AddressModel address;
}
