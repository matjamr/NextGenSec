package com.sec.gen.next.backend.internal.api.external;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Data
public class AddressModel {
    private Integer id;
    private String streetName;
    private String postalCode;
    private String city;
}
