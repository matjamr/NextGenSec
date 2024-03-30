package com.sec.gen.next.backend.image.builder;

import com.sec.gen.next.backend.api.internal.Image;
import com.sec.gen.next.backend.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class ImageLoadConsumer implements Consumer<ImageLoader> {
    private final ImageRepository imageRepository;

    @Override
    public void accept(ImageLoader imageLoader) {
        List<Image> images = imageRepository.findAllById(imageLoader.getImagesId());
        imageLoader.setImages(images);
    }
}
