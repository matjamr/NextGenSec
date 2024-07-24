package com.sec.gen.next.serviceorchestrator.internal.places.service;

import com.next.gen.sec.model.PlacesModel;
import com.sec.gen.next.serviceorchestrator.common.templates.ListQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class PlacesForUserSupplier implements Supplier< List<PlacesModel>> {
    private final ListQueryService<PlacesModel> placesModelListQueryService;

    @Override
    public List<PlacesModel> get() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return placesModelListQueryService.findAll()
                .stream()
                .filter(place -> place.getAuthorizedUsers().stream().anyMatch(userPlace -> userPlace.getUser().getEmail().equals(email)))
                .toList();
    }
}
