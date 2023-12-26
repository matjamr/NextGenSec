package com.sec.gen.next.backend.product.mapper;

import com.sec.gen.next.backend.api.external.ProductModel;
import com.sec.gen.next.backend.api.internal.Image;
import com.sec.gen.next.backend.api.internal.Product;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper
public abstract class ProductMapper {
    abstract public ProductModel from(Product product);
    abstract public  Product from(ProductModel productModel);

    @AfterMapping
    protected void updateImageRefIds(Product product, @MappingTarget ProductModel.ProductModelBuilder productModel) {
        productModel.imgIds(product.getImages().stream()
                .map(Image::getId)
                .toList());
    }
}
