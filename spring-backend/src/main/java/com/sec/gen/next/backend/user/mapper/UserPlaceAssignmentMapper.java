package com.sec.gen.next.backend.user.mapper;

import com.sec.gen.next.backend.api.external.UserPlaceAssignmentModel;
import com.sec.gen.next.backend.api.internal.UserPlaceAssignment;
import org.mapstruct.Mapper;

@Mapper(uses = {UserMapper.class})
public interface UserPlaceAssignmentMapper {
    UserPlaceAssignment from(UserPlaceAssignmentModel model);
    UserPlaceAssignmentModel from(UserPlaceAssignment userPlaceAssignment);
}
