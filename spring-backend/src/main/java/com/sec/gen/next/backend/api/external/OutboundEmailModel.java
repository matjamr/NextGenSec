package com.sec.gen.next.backend.api.external;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

@Data
@Accessors(chain = true)
public class OutboundEmailModel {
    private String strategy;
    private String email;
    private Map<String, String> params;
}
