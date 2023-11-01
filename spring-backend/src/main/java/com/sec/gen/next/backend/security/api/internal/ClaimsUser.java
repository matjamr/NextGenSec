package com.sec.gen.next.backend.security.api.internal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class ClaimsUser {
    private String email;
    private String name;
    private String familyName;
    private String pictureUrl;
    private String locale;
}
