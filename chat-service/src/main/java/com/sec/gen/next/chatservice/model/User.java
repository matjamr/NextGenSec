package com.sec.gen.next.chatservice.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class User {
    private String id;
    private String name;
    private String surname;
    private String email;
    private Role role;
}

