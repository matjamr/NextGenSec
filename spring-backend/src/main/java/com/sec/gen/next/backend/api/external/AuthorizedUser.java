package com.sec.gen.next.backend.api.external;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class AuthorizedUser {
    private String email;
    private String given_name;
    private String family_name;
    private String picture;
    private String source;
}
