package com.sec.gen.next.backend.internal.image;

import com.sec.gen.next.backend.internal.api.exception.ServiceException;
import com.sec.gen.next.backend.internal.api.external.ImageModel;
import com.sec.gen.next.backend.internal.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.sec.gen.next.backend.internal.api.exception.Error.INVALID_IMAGE_DATA;

@RequestMapping("/api/image")
@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping
    public ImageModel save(final @RequestParam("image") MultipartFile multipartFile) {
        return imageService.uploadImage(multipartFile);
    }

    @GetMapping(value = "/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] findById(@PathVariable Integer id) throws ServiceException {
        return imageService.findImageById(id)
                .orElseThrow(() -> new ServiceException(INVALID_IMAGE_DATA));
    }

    @GetMapping
    public List<ImageModel> findAll(@RequestBody List<Integer> ids) {
        return imageService.findAll(ids);
    }

}
