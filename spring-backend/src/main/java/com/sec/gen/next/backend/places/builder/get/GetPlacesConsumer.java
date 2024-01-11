package com.sec.gen.next.backend.places.builder.get;

import com.sec.gen.next.backend.api.external.PlacesModel;
import com.sec.gen.next.backend.api.internal.Places;
import com.sec.gen.next.backend.places.PlacesContext;
import com.sec.gen.next.backend.places.builder.PlacesMapper;
import com.sec.gen.next.backend.places.repository.PlacesRepository;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class GetPlacesConsumer implements Consumer<PlacesContext> {

    private final PlacesRepository placesRepository;
    private final PlacesMapper placesMapper;

    @Override
    public void accept(PlacesContext placesContext) {
        placesContext.setBatchPlacesModel(getPlaces(placesContext));
    }

    private List<PlacesModel> getPlaces(PlacesContext placesContext) {
        var retr = placesRepository.findAll();
        List<PlacesModel> ret = placesMapper.from(retr);

        if(placesContext.getUserScope()) {
            return ret.stream()
                    .filter(place -> place.getAuthorizedUsers()
                            .stream()
                            .anyMatch(userPlaceAssignmentModel -> userPlaceAssignmentModel.getUser().getEmail().equals(placesContext.getClaimsUser().getEmail())))
                    .map(place -> place.toBuilder()
                            .authorizedUsers(place.getAuthorizedUsers().stream().filter(user -> user.getUser().getEmail().equals(placesContext.getClaimsUser().getEmail())).toList())
                            .build())
                    .toList();
        }

        return ret;
    }
}
