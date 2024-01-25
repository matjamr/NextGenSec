package com.sec.gen.next.backend.internal.api.external;

import com.sec.gen.next.backend.internal.api.internal.AssignmentRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
public class UserPlaceModel {
    private String placeName;
    private AssignmentRole role;
}
