package com.sec.gen.next.backend.internal.product;

import com.sec.gen.next.backend.internal.api.exception.Error;
import com.sec.gen.next.backend.internal.api.external.ProductModel;
import com.sec.gen.next.backend.internal.api.internal.Image;
import com.sec.gen.next.backend.internal.api.internal.Product;
import com.sec.gen.next.backend.internal.image.builder.ImageLoader;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class ProductContext implements ImageLoader {
    private Product product;
    private ProductModel productModel;
    private List<ProductModel> batchProductModel;
    private List<Error> errors;
    private List<Image> images;
    private List<Product> productList;


    @Override
    public List<Integer> getImagesId() {
        return productModel.getImgIds();
    }
}
