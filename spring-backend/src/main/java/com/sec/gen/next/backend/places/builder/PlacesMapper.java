package com.sec.gen.next.backend.places.builder;

import com.sec.gen.next.backend.api.external.PlacesModel;
import com.sec.gen.next.backend.api.internal.Places;
import com.sec.gen.next.backend.common.address.AddressMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { AddressMapper.class })
public interface PlacesMapper {
    PlacesMapper INSTANCE = Mappers.getMapper(PlacesMapper.class);

    Places from(PlacesModel placesModel);
    PlacesModel from(Places places);
}
