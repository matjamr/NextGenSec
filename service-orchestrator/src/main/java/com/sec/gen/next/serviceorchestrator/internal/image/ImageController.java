package com.sec.gen.next.serviceorchestrator.internal.image;

import com.next.gen.sec.model.ImageModel;
import com.sec.gen.next.serviceorchestrator.common.templates.QueryService;
import com.sec.gen.next.serviceorchestrator.common.templates.SaveService;
import com.sec.gen.next.serviceorchestrator.common.templates.SimpleQueryService;
import com.sec.gen.next.serviceorchestrator.internal.image.util.SaveServiceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RequestMapping("/api/image")
@RestController
@RequiredArgsConstructor
public class ImageController {

    private final SaveService<List<ImageModel>, SaveServiceHelper> saveImageService;
    private final SimpleQueryService<String, byte[]> simpleImageQueryService;

    private final QueryService<ImageModel, String> imageQueryService;

    @PostMapping
    public List<ImageModel> save(final @RequestParam("images") List<MultipartFile> multipartFiles, final @RequestParam("additionalParams") List<String> imageUploadAssocs) {
        return saveImageService.save(new SaveServiceHelper(multipartFiles, imageUploadAssocs));
    }

    @GetMapping(value = "/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] findById(@PathVariable String id) {
        return simpleImageQueryService.findBy(id);
    }

    @GetMapping
    public List<ImageModel> findAll() {
        return imageQueryService.findAll();
    }
}
