package com.sec.gen.next.backend.image.mapper;

import com.sec.gen.next.backend.api.external.ImageModel;
import com.sec.gen.next.backend.api.internal.Image;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Value;

@Mapper
public abstract class ImageMapper {

    @Value("${server.url}")
    private String serverUrl;

    public ImageModel map(Image image) {
        return ImageModel.builder()
                .id(image.getId())
                .url(serverUrl + "/api/image/" + image.getId())
                .build();
    }

}
