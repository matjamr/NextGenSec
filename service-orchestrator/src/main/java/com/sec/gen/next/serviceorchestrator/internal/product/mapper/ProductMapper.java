package com.sec.gen.next.serviceorchestrator.internal.product.mapper;

import com.next.gen.api.Product;
import com.next.gen.api.SensitiveData;
import com.next.gen.sec.model.ProductModel;
import com.next.gen.sec.model.SensitiveDataModel;
import com.sec.gen.next.serviceorchestrator.internal.image.mapper.ImageMapper;
import com.sec.gen.next.serviceorchestrator.internal.places.mapper.UserMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = {ImageMapper.class, UserMapper.class})
public abstract class ProductMapper {
    abstract public ProductModel map(Product product);
    abstract public Product map(ProductModel productModel);
    abstract public List<ProductModel> map(List<Product> productModel);
    abstract public List<Product> mapList(List<ProductModel> productModel);
    abstract public SensitiveDataModel map(SensitiveData sensitiveData);
    abstract public SensitiveData map(SensitiveDataModel sensitiveDataModel);
}
