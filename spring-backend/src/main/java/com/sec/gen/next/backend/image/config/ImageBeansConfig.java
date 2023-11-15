package com.sec.gen.next.backend.image.config;


import com.sec.gen.next.backend.api.external.ImageModel;
import com.sec.gen.next.backend.api.internal.Image;
import com.sec.gen.next.backend.image.builder.ImageModelBuilder;
import com.sec.gen.next.backend.image.repository.ImageRepository;
import com.sec.gen.next.backend.image.service.ImageService;
import com.sec.gen.next.backend.image.service.ImageServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
