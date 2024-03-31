package com.sec.gen.next.backend.places;

import com.sec.gen.next.backend.api.CustomAuthentication;
import com.sec.gen.next.backend.api.external.PlacesModel;
import com.sec.gen.next.backend.places.builder.RoutingEnum;
import com.sec.gen.next.backend.common.Dispatcher;
import jakarta.servlet.ServletRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/places")
public class PlacesController {

    private final Dispatcher<List<PlacesModel>, PlacesContext, RoutingEnum> placesDispatcher;

    public PlacesController(
            @Qualifier("placesDispatcher") Dispatcher<List<PlacesModel>, PlacesContext, RoutingEnum> placesDispatcher) {
        this.placesDispatcher = placesDispatcher;
    }

    @PostMapping
    public List<PlacesModel> addPlace(
            final @Qualifier("placesContext") PlacesContext placesContext,
            final @RequestBody PlacesModel placesModel
    ) {
        return placesDispatcher.dispatch(placesContext.toBuilder()
                        .placesModel(placesModel)
                        .build(),
                RoutingEnum.ADD);
    }

    @PostMapping("/update")
    public List<PlacesModel> updatePlace(
            final @Qualifier("placesContext") PlacesContext placesContext,
            final @RequestBody PlacesModel placesModel
    ) {
        return placesDispatcher.dispatch(placesContext.toBuilder()
                        .placesModel(placesModel)
                        .build(),
                RoutingEnum.UPDATE);
    }

    @GetMapping
    public List<PlacesModel> getPlaces(
            final @Qualifier("placesContext") PlacesContext placesContext,
            final @RequestHeader(value = "user-scope", defaultValue = "false") Boolean userScope
    ) {
        return placesDispatcher.dispatch(placesContext.toBuilder()
                        .userScope(Optional.ofNullable(userScope).orElse(false))
                        .authorizedUser((CustomAuthentication) SecurityContextHolder.getContext().getAuthentication())
                        .build(),
                RoutingEnum.GET);
    }

    @DeleteMapping
    public List<PlacesModel> deletePlaces(
            final @Qualifier("placesContext") PlacesContext placesContext,
            final @RequestBody PlacesModel placesModel
    ) {
        return placesDispatcher.dispatch(placesContext.toBuilder()
                        .placesModel(placesModel)
                        .build(),
                RoutingEnum.DELETE);
    }
}
