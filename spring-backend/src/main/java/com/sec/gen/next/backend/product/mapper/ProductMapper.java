package com.sec.gen.next.backend.product.mapper;

import com.sec.gen.next.backend.api.external.ProductModel;
import com.sec.gen.next.backend.api.internal.Product;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper {
    ProductModel from(Product product);
    Product from(ProductModel productModel);
}
