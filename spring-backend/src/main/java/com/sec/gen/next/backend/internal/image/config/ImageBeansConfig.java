package com.sec.gen.next.backend.internal.image.config;


import com.sec.gen.next.backend.internal.api.external.ImageModel;
import com.sec.gen.next.backend.internal.api.internal.Image;
import com.sec.gen.next.backend.internal.image.builder.ImageLoadConsumer;
import com.sec.gen.next.backend.internal.image.builder.ImageLoader;
import com.sec.gen.next.backend.internal.image.builder.ImageModelBuilder;
import com.sec.gen.next.backend.internal.image.repository.ImageRepository;
import com.sec.gen.next.backend.internal.image.service.ImageService;
import com.sec.gen.next.backend.internal.image.service.ImageServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;
import java.util.function.Function;

@Configuration
public class ImageBeansConfig {

    @Bean
    public ImageService imageService(
            final ImageRepository imageRepository,
            final @Qualifier("imageModelBuilder") Function<Image, ImageModel> imageModelBuilder
    ) {
        return new ImageServiceImpl(imageRepository, imageModelBuilder);
    }

    @Bean("imageModelBuilder")
    public Function<Image, ImageModel> imageModelBuilder() {
        return new ImageModelBuilder();
    }

    @Bean("imageLoadConsumer")
    public Consumer<ImageLoader> imageLoadConsumer(
            final ImageRepository imageRepository
    ) {
        return new ImageLoadConsumer(imageRepository);
    }
}
