package com.sec.gen.next.serviceorchestrator.internal.places.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PlacesRequestContext {
    private Double longitude;
    private Double latitude;
    private Double kmRange;
}
