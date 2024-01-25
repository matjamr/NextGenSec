package com.sec.gen.next.backend.internal.api.external;

import com.sec.gen.next.backend.internal.api.internal.AssignmentRole;
import com.sec.gen.next.backend.internal.api.internal.VerificationStage;
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
    private VerificationStage verificationStage;
    private List<ProductModel> products;
    private UserAdd userAdd;
    private UserDelete userDelete;
    private UserModify userModify;
    private ProductAdd productAdd;

    public record UserAdd(UserModel user, AssignmentRole assignmentRole, VerificationStage verificationStage) { }

    public record UserDelete(Integer id) { }

    public record UserModify(UserModel user, AssignmentRole assignmentRole) { }
    public record ProductAdd(UserModel user, List<ProductModel> products) { }
}
