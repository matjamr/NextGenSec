package com.sec.gen.next.serviceorchestrator.internal.product.service.user;

import com.next.gen.sec.model.ImageModel;
import com.next.gen.sec.model.SensitiveDataModel;
import com.sec.gen.next.serviceorchestrator.common.templates.DeleteService;
import com.sec.gen.next.serviceorchestrator.internal.image.repository.ImageRepository;
import com.sec.gen.next.serviceorchestrator.internal.product.repository.SensitiveDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
public class UserProductDeleteService implements DeleteService<SensitiveDataModel, SensitiveDataModel> {
    private final SensitiveDataRepository sensitiveDataRepository;
    private final ImageRepository imageRepository;

    @Override
    public SensitiveDataModel delete(SensitiveDataModel sensitiveDataModel) {

        if (!CollectionUtils.isEmpty(sensitiveDataModel.getImages())) {
            imageRepository.deleteAllByIdInBatch(sensitiveDataModel.getImages().stream().map(ImageModel::getId).toList());
        } else {
            sensitiveDataRepository.deleteById(sensitiveDataModel.getId());
        }

        return sensitiveDataModel;
    }
}
