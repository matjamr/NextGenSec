package com.sec.gen.next.serviceorchestrator.internal.product.service.user;

import com.next.gen.api.User;
import com.next.gen.sec.model.SensitiveDataModel;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Component
public class IdBasedSensitiveDataRemovalConsumer implements BiConsumer<SensitiveDataModel, User> {
    @Override
    public void accept(SensitiveDataModel sensitiveDataModel, User user) {
        user.getSensitiveData()
                .removeIf(sensitiveData -> sensitiveData.getProduct().getId().equals(sensitiveDataModel.getProduct().getId()));
    }
}
