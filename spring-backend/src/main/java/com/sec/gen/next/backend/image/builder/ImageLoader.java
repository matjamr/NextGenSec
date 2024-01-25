package com.sec.gen.next.backend.image.builder;

import com.sec.gen.next.backend.api.internal.Image;

import java.util.List;

public interface ImageLoader {
    void setImages(List<Image> images);
    List<Integer> getImagesId();
}
