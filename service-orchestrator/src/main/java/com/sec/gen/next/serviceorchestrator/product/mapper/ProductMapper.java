package com.sec.gen.next.serviceorchestrator.product.mapper;

import com.next.gen.api.Product;
import com.next.gen.sec.model.ProductModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public abstract class ProductMapper {
    abstract public ProductModel map(Product product);
    abstract public Product map(ProductModel productModel);
    abstract public List<ProductModel> map(List<Product> productModel);
    abstract public List<Product> mapList(List<ProductModel> productModel);
}
