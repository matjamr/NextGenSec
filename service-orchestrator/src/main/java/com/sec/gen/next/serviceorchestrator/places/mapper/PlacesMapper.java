package com.sec.gen.next.serviceorchestrator.places.mapper;

import com.next.gen.api.Device;
import com.next.gen.api.Places;
import com.next.gen.api.User;
import com.next.gen.sec.model.PlacesModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UserPlaceAssignmentMapper.class, UserMapper.class, DeviceMapper.class})
public interface PlacesMapper {

    PlacesModel map(Places placeEntity);
    Places map(PlacesModel placesModel);
}
