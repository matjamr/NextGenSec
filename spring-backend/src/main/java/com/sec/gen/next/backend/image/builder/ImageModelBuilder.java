package com.sec.gen.next.backend.image.builder;

import com.sec.gen.next.backend.api.external.ImageModel;
import com.sec.gen.next.backend.api.internal.Image;
import org.springframework.beans.factory.annotation.Value;

import java.util.function.Function;

public class ImageModelBuilder implements Function<Image, ImageModel> {

    @Value("${server.url}")
    private String serverUrl;

    @Override
    public ImageModel apply(Image image) {
        return ImageModel.builder()
                .id(image.getId())
                .url(serverUrl + "/api/image/" + image.getId())
                .build();
    }
}
