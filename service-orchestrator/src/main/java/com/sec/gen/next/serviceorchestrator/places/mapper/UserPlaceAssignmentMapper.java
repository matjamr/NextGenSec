package com.sec.gen.next.serviceorchestrator.places.mapper;

import com.next.gen.api.UserPlaceAssignment;
import com.sec.gen.next.serviceorchestrator.product.mapper.ProductMapper;
import org.mapstruct.Mapper;

@Mapper(uses = {UserMapper.class, ProductMapper.class})
public interface UserPlaceAssignmentMapper {
    UserPlaceAssignment map(com.next.gen.sec.model.UserPlaceAssignment userPlaceAssignment);
    com.next.gen.sec.model.UserPlaceAssignment map(UserPlaceAssignment userPlaceAssignment);
}
