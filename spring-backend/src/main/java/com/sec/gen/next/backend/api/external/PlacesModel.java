package com.sec.gen.next.backend.api.external;

import com.sec.gen.next.backend.api.internal.VerificationStage;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Builder(toBuilder = true)
@Data
public class PlacesModel {
    private Integer id;
    private String placeName;
    private String emailPlace;
    private AddressModel address;
    private List<UserPlaceAssignmentModel> authorizedUsers;
    private VerificationStage verificationStage;
    private List<PlacesModel> batchRetrieve;
}
