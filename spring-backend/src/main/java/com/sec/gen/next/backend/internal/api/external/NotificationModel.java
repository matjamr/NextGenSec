package com.sec.gen.next.backend.internal.api.external;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
@Data
public class NotificationModel {

    private Integer id;
    private String title;
    private String content;
    private UserModel user;
    private LocalDateTime date;
}
