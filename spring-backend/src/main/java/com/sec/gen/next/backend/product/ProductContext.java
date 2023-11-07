package com.sec.gen.next.backend.product;

import com.sec.gen.next.backend.api.exception.Error;
import com.sec.gen.next.backend.api.external.PlacesModel;
import com.sec.gen.next.backend.api.external.ProductModel;
import com.sec.gen.next.backend.api.internal.Places;
import com.sec.gen.next.backend.api.internal.Product;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class ProductContext {
    private Product product;
    private ProductModel productModel;
    private List<ProductModel> batchProductModel;
    private List<Error> errors;
}
