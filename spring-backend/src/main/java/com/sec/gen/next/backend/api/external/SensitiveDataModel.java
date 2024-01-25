package com.sec.gen.next.backend.api.external;

import lombok.Builder;
import lombok.Data;

@Builder(toBuilder = true)
@Data
public class SensitiveDataModel {

    private String id;
    private ImageModel image;
    private ProductModel product;
}
