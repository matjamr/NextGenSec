package com.sec.gen.next.serviceorchestrator.internal.places.mapper;

import com.next.gen.api.Places;
import com.next.gen.sec.model.PlacesModel;
import com.sec.gen.next.serviceorchestrator.internal.device.mapper.DeviceMapper;
import org.mapstruct.Mapper;

@Mapper(uses = {UserPlaceAssignmentMapper.class, UserMapper.class, DeviceMapper.class})
public interface PlacesMapper {

    PlacesModel map(Places placeEntity);
    Places map(PlacesModel placesModel);
}
