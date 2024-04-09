package com.sec.gen.next.serviceorchestrator.image.service;

import com.next.gen.api.custom.BetterOptional;
import com.next.gen.sec.model.ImageModel;
import com.sec.gen.next.serviceorchestrator.common.templates.SaveService;
import com.sec.gen.next.serviceorchestrator.image.mapper.ImageMapper;
import com.sec.gen.next.serviceorchestrator.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import static com.sec.gen.next.serviceorchestrator.exception.Error.*;

@RequiredArgsConstructor
public class SaveImageService implements SaveService<ImageModel, MultipartFile> {

    private final ImageMapper imageMapper;
    private final ImageRepository imageRepository;

    @Override
    public ImageModel save(MultipartFile multipartFile)  {
        return BetterOptional.of(multipartFile)
                .verify(() -> isAllowedExtension(multipartFile), INVALID_IMAGE_EXTENSION.getError())
                .verify(() -> isAllowedSize(multipartFile), INVALID_IMAGE_SIZE.getError())
                .optionalMap(imageMapper::map)
                .map(imageRepository::save)
                .map(imageMapper::map)
                .orElseThrow(INVALID_IMAGE_DATA::getError);
    }

    private Boolean isAllowedSize(MultipartFile multipartFile) {
        return true;
    }

    private Boolean isAllowedExtension(MultipartFile multipartFile) {
        return true;
    }
}
