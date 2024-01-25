package com.sec.gen.next.backend.internal.places;

import com.sec.gen.next.backend.internal.api.exception.Error;
import com.sec.gen.next.backend.internal.api.external.PlacesModel;
import com.sec.gen.next.backend.internal.api.internal.ClaimsUser;
import com.sec.gen.next.backend.internal.api.internal.Places;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class PlacesContext {
    private Places places;
    private PlacesModel placesModel;
    private List<PlacesModel> batchPlacesModel;
    private List<Places> batchPlaces;
    private List<Error> errors;
    private Boolean userScope = false;
    private ClaimsUser claimsUser;
}
