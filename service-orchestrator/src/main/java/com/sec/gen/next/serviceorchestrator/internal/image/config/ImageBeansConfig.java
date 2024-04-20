package com.sec.gen.next.serviceorchestrator.internal.image.config;


import com.next.gen.sec.model.ImageModel;
import com.sec.gen.next.serviceorchestrator.common.templates.QueryService;
import com.sec.gen.next.serviceorchestrator.common.templates.SaveService;
import com.sec.gen.next.serviceorchestrator.common.templates.SimpleQueryService;
import com.sec.gen.next.serviceorchestrator.internal.image.mapper.ImageMapper;
import com.sec.gen.next.serviceorchestrator.internal.image.repository.ImageRepository;
import com.sec.gen.next.serviceorchestrator.internal.image.service.ImageQueryService;
import com.sec.gen.next.serviceorchestrator.internal.image.service.SaveImageService;
import com.sec.gen.next.serviceorchestrator.internal.image.service.SimpleImageQueryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Configuration
public class ImageBeansConfig {

    @Bean
    public SaveService<List<ImageModel>, List<MultipartFile>> saveImageService(
            final ImageMapper imageMapper,
            final ImageRepository imageRepository
            ) {
        return new SaveImageService(imageMapper, imageRepository);
    }

    @Bean
    public SimpleQueryService<String, byte[]> simpleQueryService(
            final ImageRepository imageRepository
    ) {
        return new SimpleImageQueryService(imageRepository);
    }

    @Bean
    public QueryService<ImageModel, String> imageQueryService(
            final ImageMapper imageMapper,
            final ImageRepository imageRepository
    ) {
        return new ImageQueryService(imageMapper, imageRepository);
    }
}
