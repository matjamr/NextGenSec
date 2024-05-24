package com.sec.gen.next.serviceorchestrator.internal.places.mapper.viewership;

import com.next.gen.sec.model.PlacesModel;
import com.sec.gen.next.serviceorchestrator.api.CustomAuthentication;
import com.sec.gen.next.serviceorchestrator.common.templates.ViewershipBuilder;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;

public class PlacesUserViewershipBuilder implements ViewershipBuilder<List<PlacesModel>> {

    @Override
    public List<PlacesModel> apply(List<PlacesModel> obj) {
//        CustomAuthentication user = (CustomAuthentication) SecurityContextHolder.getContext().getAuthentication();
//
//        return obj.stream()
//                .filter(palce -> palce.getAuthorizedUsers().stream().anyMatch(u -> u.getUser().getEmail().equals(user.getEmail())))
//                .map(place -> place.authorizedUsers(Collections.emptyList()))
//                .toList();

        return obj;
    }
}
