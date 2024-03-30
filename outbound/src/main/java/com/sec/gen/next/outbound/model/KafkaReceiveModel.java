package com.sec.gen.next.outbound.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

@Data
@Accessors(chain = true)
public class KafkaReceiveModel {
    private String strategy;
    private String email;
    private Map<String, String> params;
}
