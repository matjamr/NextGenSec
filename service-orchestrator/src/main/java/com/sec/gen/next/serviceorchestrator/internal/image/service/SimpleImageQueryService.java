package com.sec.gen.next.serviceorchestrator.internal.image.service;

import com.next.gen.api.Image;
import com.sec.gen.next.serviceorchestrator.common.templates.SimpleQueryService;
import com.sec.gen.next.serviceorchestrator.internal.image.repository.ImageRepository;
import com.sec.gen.next.serviceorchestrator.security.service.aes.AesHandler;
import lombok.RequiredArgsConstructor;

import static com.sec.gen.next.serviceorchestrator.exception.Error.INVALID_IMAGE_ID;

@RequiredArgsConstructor
public class SimpleImageQueryService implements SimpleQueryService<String, byte[]> {

    private final ImageRepository imageRepository;

    @Override
    public byte[] findBy(String id) {
        return imageRepository.findById(id)
                .map(this::handleSensitive)
                .orElseThrow(INVALID_IMAGE_ID::getError);
    }

    private byte[] handleSensitive(Image image) {
        return image.isSensitive() ? AesHandler.decrypt(image.getContent()) : image.getContent();
    }
}
