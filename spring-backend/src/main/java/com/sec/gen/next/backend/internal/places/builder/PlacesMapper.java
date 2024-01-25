package com.sec.gen.next.backend.internal.places.builder;

import com.sec.gen.next.backend.internal.api.external.PlacesModel;
import com.sec.gen.next.backend.internal.api.internal.Places;
import com.sec.gen.next.backend.internal.common.address.AddressMapper;
import com.sec.gen.next.backend.internal.user.mapper.UserPlaceAssignmentMapper;
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
