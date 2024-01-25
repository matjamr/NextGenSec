package com.sec.gen.next.backend.api.external;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
@Data
public class NewsModel {
    private Integer id;
    private String title;
    private String description;
    private Integer imageId;
    private LocalDateTime lastUpdate;
    private String imageUrl;
}
