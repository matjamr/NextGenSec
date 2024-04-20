package com.sec.gen.next.serviceorchestrator.internal.image.service;

import com.next.gen.api.custom.BetterOptional;
import com.next.gen.sec.model.ImageModel;
import com.sec.gen.next.serviceorchestrator.common.templates.SaveService;
import com.sec.gen.next.serviceorchestrator.internal.image.mapper.ImageMapper;
import com.sec.gen.next.serviceorchestrator.internal.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static com.sec.gen.next.serviceorchestrator.exception.Error.*;

@RequiredArgsConstructor
public class SaveImageService implements SaveService<List<ImageModel>, List<MultipartFile>> {

    private final ImageMapper imageMapper;
    private final ImageRepository imageRepository;

    @Override
    public List<ImageModel> save(List<MultipartFile> multipartFile)  {
        return Optional.of(multipartFile)
                .map(images -> images.stream().map(imageMapper::map).toList())
                .map(imageRepository::saveAll)
                .map(images -> images.stream().map(imageMapper::map).toList())
                .orElseThrow(INVALID_IMAGE_DATA::getError);
    }

    private Boolean isAllowedSize(MultipartFile multipartFile) {
        return true;
    }

    private Boolean isAllowedExtension(MultipartFile multipartFile) {
        return true;
    }
}
