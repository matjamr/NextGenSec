package com.sec.gen.next.backend.places;

import com.sec.gen.next.backend.api.external.PlacesModel;
import com.sec.gen.next.backend.places.builder.common.PlacesRoutingEnum;
import com.sec.gen.next.backend.common.Dispatcher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/places")
public class PlacesController {

    private final Dispatcher<PlacesModel, PlacesContext, PlacesRoutingEnum> placesDispatcher;

    public PlacesController(
            @Qualifier("placesDispatcher") Dispatcher<PlacesModel, PlacesContext, PlacesRoutingEnum> placesDispatcher) {
        this.placesDispatcher = placesDispatcher;
    }

    @PostMapping
    public PlacesModel addPlace(
            final @Qualifier("placesContext") PlacesContext placesContext,
            final @RequestBody PlacesModel placesModel
    ) {
        return placesDispatcher.dispatch(placesContext.toBuilder()
                        .placesModel(placesModel)
                        .build(),
                PlacesRoutingEnum.ADD);
    }

    @PutMapping
    public PlacesModel updatePlace(
            final @Qualifier("placesContext") PlacesContext placesContext,
            final @RequestBody PlacesModel placesModel
    ) {
        return placesDispatcher.dispatch(placesContext.toBuilder()
                        .placesModel(placesModel)
                        .build(),
                PlacesRoutingEnum.UPDATE);
    }

    @GetMapping
    public PlacesModel getPlaces(
            final @Qualifier("placesContext") PlacesContext placesContext,
            final @RequestBody PlacesModel placesModel
    ) {
        return placesDispatcher.dispatch(placesContext.toBuilder()
                        .placesModel(placesModel)
                        .build(),
                PlacesRoutingEnum.GET);
    }

    @DeleteMapping
    public PlacesModel deletePlaces(
            final @Qualifier("placesContext") PlacesContext placesContext,
            final @RequestBody PlacesModel placesModel
    ) {
        return placesDispatcher.dispatch(placesContext.toBuilder()
                        .placesModel(placesModel)
                        .build(),
                PlacesRoutingEnum.DELETE);
    }
}
