package com.sec.gen.next.backend.internal.api.external;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Builder
@Data
public class ImageModel {
    private Integer id;
    private String url;
}
