package com.sec.gen.next.backend.places.builder;

import com.sec.gen.next.backend.api.external.PlacesModel;
import com.next.gen.api.Places;
import com.sec.gen.next.backend.common.address.AddressMapper;
import com.sec.gen.next.backend.user.mapper.UserPlaceAssignmentMapper;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = { AddressMapper.class, UserPlaceAssignmentMapper.class},
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface PlacesMapper {
    Places from(PlacesModel placesModel);
    List<Places> fromModel(List<PlacesModel> placesModel);
    PlacesModel from(Places places);
    List<PlacesModel> from(List<Places> places);
}
