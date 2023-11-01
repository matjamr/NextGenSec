package com.sec.gen.next.backend.api.external;

import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class PlacesModel {
    private Integer id;
    private String placeName;
    private String emailPlace;
    private AddressModel addressModel;
}
