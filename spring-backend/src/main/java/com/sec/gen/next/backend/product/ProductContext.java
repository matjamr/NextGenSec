package com.sec.gen.next.backend.product;

import com.sec.gen.next.backend.api.exception.Error;
import com.sec.gen.next.backend.api.external.ProductModel;
import com.sec.gen.next.backend.api.internal.Image;
import com.sec.gen.next.backend.api.internal.Product;
import com.sec.gen.next.backend.image.builder.ImageLoader;
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


    @Override
    public List<Integer> getImagesId() {
        return productModel.getImgUrls();
    }
}
