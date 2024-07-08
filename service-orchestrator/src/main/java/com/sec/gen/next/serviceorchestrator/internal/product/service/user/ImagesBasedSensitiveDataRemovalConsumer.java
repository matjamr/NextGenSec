package com.sec.gen.next.serviceorchestrator.internal.product.service.user;

import com.next.gen.api.SensitiveData;
import com.next.gen.api.User;
import com.next.gen.sec.model.ImageModel;
import com.next.gen.sec.model.SensitiveDataModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BiConsumer;

import static com.sec.gen.next.serviceorchestrator.exception.Error.INVALID_SENSITIVE_DATA_ID;

@Component
public class ImagesBasedSensitiveDataRemovalConsumer implements BiConsumer<SensitiveDataModel, User> {

    @Override
    public void accept(SensitiveDataModel sensitiveDataModel, User user) {
        String id = sensitiveDataModel.getId();
        List<ImageModel> imagesToBeRemoved = sensitiveDataModel.getImages();

        SensitiveData sensitiveData = user.getSensitiveData()
                .stream()
                .filter(data -> data.getId().equals(id))
                .findFirst()
                .orElseThrow(INVALID_SENSITIVE_DATA_ID::getError);


        sensitiveData.getImages()
                .removeIf(image -> imagesToBeRemoved.stream()
                        .anyMatch(imageToBeRemoved -> imageToBeRemoved.getId().equals(image.getId())));

        user.getSensitiveData().replaceAll(data -> data.getId().equals(id) ? sensitiveData : data);
    }
}
