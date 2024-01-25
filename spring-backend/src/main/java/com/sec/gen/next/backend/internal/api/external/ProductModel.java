package com.sec.gen.next.backend.internal.api.external;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder(toBuilder = true)
@Data
public class ProductModel {
    private Integer id;
    private String name;
    private String description;
    private Double monthlyPrice;
    private List<Integer> imgIds;
}
