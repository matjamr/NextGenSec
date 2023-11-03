package com.sec.gen.next.backend.places.builder;

import com.sec.gen.next.backend.api.external.PlacesModel;
import com.sec.gen.next.backend.api.internal.Places;
import com.sec.gen.next.backend.common.address.AddressMapper;
import com.sec.gen.next.backend.user.mapper.UserPlaceAssignmentMapper;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { AddressMapper.class, UserPlaceAssignmentMapper.class},
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface PlacesMapper {
    Places from(PlacesModel placesModel);
    PlacesModel from(Places places);
}
