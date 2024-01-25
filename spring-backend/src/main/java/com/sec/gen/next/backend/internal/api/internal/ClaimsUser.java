package com.sec.gen.next.backend.internal.api.internal;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Builder(toBuilder = true)
public class ClaimsUser {
    private String email;
    private String name;
    private String familyName;
    private String pictureUrl;
    private String locale;
}
