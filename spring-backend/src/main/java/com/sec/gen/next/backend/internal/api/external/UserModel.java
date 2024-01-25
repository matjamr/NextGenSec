package com.sec.gen.next.backend.internal.api.external;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
public class UserModel {
    private Integer id;
    private String email;
}
