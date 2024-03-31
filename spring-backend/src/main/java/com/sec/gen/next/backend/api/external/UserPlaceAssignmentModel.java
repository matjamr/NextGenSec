package com.sec.gen.next.backend.api.external;

import com.next.gen.api.AssignmentRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder(toBuilder = true)
public class UserPlaceAssignmentModel {
    private Integer id;
    private UserModel user;
    private AssignmentRole assignmentRole;
    private List<ProductModel> products;
    private UserAdd userAdd;
    private UserDelete userDelete;
    private UserModify userModify;
    private ProductAdd productAdd;

    public record UserAdd(UserModel user, AssignmentRole assignmentRole) { }

    public record UserDelete(Integer id) { }

    public record UserModify(UserModel user, AssignmentRole assignmentRole) { }
    public record ProductAdd(UserModel user, List<ProductModel> products) { }
}
