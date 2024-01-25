package com.sec.gen.next.backend.image.mapper;

import com.sec.gen.next.backend.api.external.ImageModel;
import com.sec.gen.next.backend.api.internal.Image;
import org.mapstruct.Mapper;

@Mapper
public interface ImageMapper {

    default ImageModel map(Image image) {
        return ImageModel.builder()
                .id(image.getId())
                .url("http://localhost:8080/api/image/" + image.getId())
                .build();
    }

}
