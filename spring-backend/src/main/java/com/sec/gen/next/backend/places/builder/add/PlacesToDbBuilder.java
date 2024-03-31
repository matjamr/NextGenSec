package com.sec.gen.next.backend.places.builder.add;

import com.sec.gen.next.backend.api.external.UserModel;
import com.sec.gen.next.backend.api.external.UserPlaceAssignmentModel;
import com.next.gen.api.Places;
import com.next.gen.api.User;
import com.next.gen.api.UserPlaceAssignment;
import com.sec.gen.next.backend.common.Service;
import com.sec.gen.next.backend.places.repository.PlacesRepository;
import com.sec.gen.next.backend.places.PlacesContext;
import com.sec.gen.next.backend.places.builder.PlacesMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@RequiredArgsConstructor
public class PlacesToDbBuilder implements Consumer<PlacesContext> {

    private final PlacesRepository placesRepository;
    private final Function<List<UserPlaceAssignmentModel>, List<UserPlaceAssignment>> userPlaceAssignmentToDb;
    private final Consumer<PlacesContext> dynamicUpdater;
    private final PlacesMapper placesMapper;
    private final Service<User, UserModel> addUserService;

    @Override
    public void accept(PlacesContext placesContext) {
        dynamicUpdater.accept(placesContext);
//        addUserService.doService(placesContext.getPlacesModel().getUserModel());

        userPlaceAssignmentToDb.apply(placesContext.getPlacesModel().getAuthorizedUsers());
        Places places = placesMapper.from(placesContext.getPlacesModel());

        places = placesRepository.save(places);
        placesContext.setBatchPlacesModel(List.of(placesMapper.from(places)));
    }
}
