package com.sec.gen.next.backend.internal.image.service;

import com.sec.gen.next.backend.internal.api.exception.ServiceException;
import com.sec.gen.next.backend.internal.api.external.ImageModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ImageService {

    ImageModel uploadImage(MultipartFile imageContext) throws ServiceException;

    Optional<byte[]> findImageById(Integer id);

    List<ImageModel> findAll(List<Integer> ids);
}
