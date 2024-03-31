package com.sec.gen.next.backend.places;

import com.sec.gen.next.backend.api.CustomAuthentication;
import com.sec.gen.next.backend.api.exception.Error;
import com.sec.gen.next.backend.api.external.AuthorizedUser;
import com.sec.gen.next.backend.api.external.PlacesModel;
import com.next.gen.api.Places;
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
    private CustomAuthentication authorizedUser;
}
