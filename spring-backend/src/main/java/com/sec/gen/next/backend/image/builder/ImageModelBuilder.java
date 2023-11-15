package com.sec.gen.next.backend.image.builder;

import com.sec.gen.next.backend.api.external.ImageModel;
import com.sec.gen.next.backend.api.internal.Image;

import java.util.function.Function;

public class ImageModelBuilder implements Function<Image, ImageModel> {
    @Override
    public ImageModel apply(Image image) {
        return ImageModel.builder()
                .id(image.getId())
                .url("http://localhost:8080/api/image/" + image.getId())
                .build();
    }
}
