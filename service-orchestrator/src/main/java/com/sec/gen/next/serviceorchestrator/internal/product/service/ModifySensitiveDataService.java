package com.sec.gen.next.serviceorchestrator.internal.product.service;

import com.next.gen.api.Image;
import com.next.gen.api.SensitiveData;
import com.next.gen.api.State;
import com.next.gen.api.User;
import com.next.gen.sec.model.ImageModel;
import com.next.gen.sec.model.SensitiveDataModel;
import com.sec.gen.next.serviceorchestrator.common.templates.ModifyService;
import com.sec.gen.next.serviceorchestrator.internal.email.repository.UserRepository;
import com.sec.gen.next.serviceorchestrator.internal.product.repository.SensitiveDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static com.sec.gen.next.serviceorchestrator.exception.Error.INVALID_SENSITIVE_DATA_ID;

@Service
@RequiredArgsConstructor
public class ModifySensitiveDataService implements ModifyService<SensitiveDataModel> {

    private final SensitiveDataRepository sensitiveDataRepository;

    @Override
    public void update(SensitiveDataModel sensitiveDataModel) {
        var data = sensitiveDataRepository.findById(sensitiveDataModel.getId())
                .orElseThrow(INVALID_SENSITIVE_DATA_ID::getError);

        Optional.ofNullable(sensitiveDataModel.getState())
                .ifPresent(state -> data.setState(State.valueOf(state)));

        Optional.ofNullable(sensitiveDataModel.getImages())
                        .ifPresent(images -> updateImages(data, images));

        sensitiveDataRepository.save(data);
    }

    private void updateImages(SensitiveData data, List<ImageModel> images) {
        data.getImages().clear();
        images.stream()
                .map(image -> new Image().setId(image.getId()))
                .forEach(data.getImages()::add);
        data.setState(State.NOT_VERIFIED);
    }

}
