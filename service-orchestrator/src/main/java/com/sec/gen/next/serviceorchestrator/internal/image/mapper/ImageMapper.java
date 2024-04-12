package com.sec.gen.next.serviceorchestrator.internal.image.mapper;

import com.next.gen.api.Image;
import com.next.gen.sec.model.ImageModel;
import lombok.SneakyThrows;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public abstract class ImageMapper {

    @Value("${server.url}")
    private String serverUrl;

    public List<String> mapModel(List<ImageModel> value) {
        return value.stream()
                .map(ImageModel::getId)
                .toList();
    }

    public List<ImageModel> map(List<String> value) {
        return value.stream()
                .map(id -> new ImageModel()
                        .id(id)
                        .url(serverUrl + "/api/image/" + id))
                .toList();
    }

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
