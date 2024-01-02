package com.sec.gen.next.backend.api.external;

import com.sec.gen.next.backend.api.internal.AssignmentRole;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Builder(toBuilder = true)
public class UserPlaceAssignmentModel {
    private Integer id;
    private UserModel user;
    private AssignmentRole assignmentRole;
    private UserAdd userAdd;
    private UserDelete userDelete;
    private UserModify userModify;

    public record UserAdd(UserModel user, AssignmentRole assignmentRole) { }

    public record UserDelete(Integer id) { }

    public record UserModify(UserModel user, AssignmentRole assignmentRole) { }
}
