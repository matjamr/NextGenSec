package com.sec.gen.next.backend.api.external;


import com.fasterxml.jackson.annotation.JacksonAnnotation;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sec.gen.next.backend.api.internal.AssignmentRole;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class KafkaNotifModel {
    private String email;

    @JsonProperty("place_id")
    private Integer placeId;

    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("assignment_role")
    private AssignmentRole assignmentRole;

    @JsonProperty("device_id")
    private Integer deviceId;
}
