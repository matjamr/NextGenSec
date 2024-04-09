package com.sec.gen.next.serviceorchestrator.image.mapper;

import com.next.gen.api.Image;
import com.next.gen.sec.model.ImageModel;
import lombok.SneakyThrows;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

@Mapper
public abstract class ImageMapper {

    @Value("${server.url}")
    private String serverUrl;

    public ImageModel map(Image image) {
        return new ImageModel()
                .id(image.getId())
                .url(serverUrl + "/api/image/" + image.getId());
    }

    @SneakyThrows
    public Image map(MultipartFile multipartFile) {
        return new Image()
                .setContent(multipartFile.getBytes())
                .setTitle(multipartFile.getOriginalFilename())
                .setExtension(multipartFile.getContentType());
    }

}
