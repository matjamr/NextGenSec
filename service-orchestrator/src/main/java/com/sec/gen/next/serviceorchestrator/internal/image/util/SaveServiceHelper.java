package com.sec.gen.next.serviceorchestrator.internal.image.util;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record SaveServiceHelper(List<MultipartFile> multipartFiles, List<String> imageUploadAssocs) {
}
