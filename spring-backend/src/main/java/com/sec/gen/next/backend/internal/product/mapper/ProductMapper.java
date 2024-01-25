package com.sec.gen.next.backend.internal.product.mapper;

import com.sec.gen.next.backend.internal.api.external.ProductModel;
import com.sec.gen.next.backend.internal.api.internal.Image;
import com.sec.gen.next.backend.internal.api.internal.Product;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public abstract class ProductMapper {
    abstract public ProductModel from(Product product);
    abstract public  Product from(ProductModel productModel);
    abstract public List<ProductModel> from(List<Product> productModel);
    abstract public List<Product> fromList(List<ProductModel> productModel);


    @AfterMapping
    protected void updateImageRefIds(Product product, @MappingTarget ProductModel.ProductModelBuilder productModel) {
        productModel.imgIds(product.getImages().stream()
                .map(Image::getId)
                .toList());
    }
}
