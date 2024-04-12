package com.sec.gen.next.serviceorchestrator.internal.image.service;

import com.next.gen.sec.model.ImageModel;
import com.sec.gen.next.serviceorchestrator.common.templates.QueryService;
import com.sec.gen.next.serviceorchestrator.internal.image.mapper.ImageMapper;
import com.sec.gen.next.serviceorchestrator.internal.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.sec.gen.next.serviceorchestrator.exception.Error.INVALID_IMAGE_ID;

@RequiredArgsConstructor
public class ImageQueryService implements QueryService<ImageModel, String> {

    private final ImageMapper imageMapper;
    private final ImageRepository imageRepository;

    @Override
    public List<ImageModel> findAll() {
        return imageRepository.findAll()
                .stream()
                .map(imageMapper::map)
                .toList();
    }

    @Override
    public ImageModel findBy(String id) {
        return imageRepository.findById(id)
                .map(imageMapper::map)
                .orElseThrow(INVALID_IMAGE_ID::getError);
    }
}
