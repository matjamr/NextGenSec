package com.sec.gen.next.backend.internal.api.external;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Data
public class AdditionalInformationUpdateModel {
    private String name;
    private String surname;
    private String phoneNumber;
    private AddressModel addressModel;
}
