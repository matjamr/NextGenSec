package com.sec.gen.next.serviceorchestrator.internal.image.service;

import com.next.gen.api.Image;
import com.next.gen.sec.model.ImageModel;
import com.sec.gen.next.serviceorchestrator.common.templates.SaveService;
import com.sec.gen.next.serviceorchestrator.internal.image.mapper.ImageMapper;
import com.sec.gen.next.serviceorchestrator.internal.image.repository.ImageRepository;
import com.sec.gen.next.serviceorchestrator.internal.image.util.SaveServiceHelper;
import com.sec.gen.next.serviceorchestrator.security.service.aes.AesHandler;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.sec.gen.next.serviceorchestrator.exception.Error.INVALID_IMAGE_DATA;

@RequiredArgsConstructor
public class SaveImageService implements SaveService<List<ImageModel>, SaveServiceHelper> {

    private final ImageMapper imageMapper;
    private final ImageRepository imageRepository;

    @Override
    public List<ImageModel> save(SaveServiceHelper saveServiceHelper)  {
        return Optional.of(saveServiceHelper.multipartFiles())
                .map(images -> images.stream()
                        .map(imageMapper::map)
                        .toList()
                )
                .map(mappedImages -> handleEncryption(mappedImages, saveServiceHelper.imageUploadAssocs()))
                .map(imageRepository::saveAll)
                .map(images -> images.stream().map(imageMapper::map).toList())
                .orElseThrow(INVALID_IMAGE_DATA::getError);
    }

    private List<Image> handleEncryption(List<Image> mappedImages, List<String> imageUploadAssocs) {
        return mappedImages.stream()
                .peek(image -> imageUploadAssocs.stream()
                        .filter(imageUploadAssoc -> imageUploadAssoc.equals(image.getTitle()))
                        .findFirst()
                        .ifPresent(imageUploadAssoc -> handleEncryption(image)))
                .toList();
    }

    private void handleEncryption(Image image) {
        image.setContent(AesHandler.encrypt(image.getContent())).setSensitive(true);
    }
}
