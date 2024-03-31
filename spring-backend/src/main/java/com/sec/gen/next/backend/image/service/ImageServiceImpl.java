package com.sec.gen.next.backend.image.service;

import com.sec.gen.next.backend.api.exception.Error;
import com.sec.gen.next.backend.api.exception.ServiceException;
import com.sec.gen.next.backend.api.external.ImageModel;
import com.next.gen.api.Image;
import com.sec.gen.next.backend.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final Function<Image, ImageModel> imageModelBuilder;


    @Override
    public ImageModel uploadImage(MultipartFile multipartFile) throws ServiceException {
        try {
            return Optional.of(imageRepository.save(buildImage(multipartFile)))
                    .map(imageModelBuilder)
                    .orElseThrow(() -> new ServiceException(Error.INVALID_IMAGE_DATA));
        } catch (Exception e) {
            throw new ServiceException(Error.INVALID_IMAGE_DATA);
        }
    }

    @Override
    public Optional<byte[]> findImageById(Integer id) {
        return imageRepository.findById(id)
                .map(Image::getContent);
    }

    @Override
    public List<ImageModel> findAll(List<Integer> ids) {
        return imageRepository.findAll().stream()
                .filter(image -> ids.contains(image.getId()))
                .map(imageModelBuilder)
                .toList();
    }

    private Image buildImage(MultipartFile file) throws IOException {
        return new Image()
                .setTitle(file.getOriginalFilename())
                .setContent(file.getBytes());
    }
}
