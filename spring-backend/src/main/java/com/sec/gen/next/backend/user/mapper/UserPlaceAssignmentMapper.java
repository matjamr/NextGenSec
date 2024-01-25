package com.sec.gen.next.backend.user.mapper;

import com.sec.gen.next.backend.api.external.UserPlaceAssignmentModel;
import com.sec.gen.next.backend.api.internal.UserPlaceAssignment;
import com.sec.gen.next.backend.product.mapper.ProductMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {UserMapper.class, ProductMapper.class})
public interface UserPlaceAssignmentMapper {
    UserPlaceAssignment from(UserPlaceAssignmentModel model);
    @Mapping(source = "userPlaceAssignment.products", target = "products")
    UserPlaceAssignmentModel from(UserPlaceAssignment userPlaceAssignment);
}
