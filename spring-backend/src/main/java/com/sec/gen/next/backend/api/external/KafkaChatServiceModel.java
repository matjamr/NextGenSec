package com.sec.gen.next.backend.api.external;

import com.sec.gen.next.backend.api.internal.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Builder
@Data
public class KafkaChatServiceModel {
    private List<String> adminsEmails;
    private String userEmail;
    private String name;
    private String surname;
    private String method;
}
