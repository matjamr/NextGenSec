package com.sec.gen.next.backend.places;

import com.sec.gen.next.backend.api.exception.Error;
import com.sec.gen.next.backend.api.external.PlacesModel;
import com.sec.gen.next.backend.api.internal.Places;
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
}
