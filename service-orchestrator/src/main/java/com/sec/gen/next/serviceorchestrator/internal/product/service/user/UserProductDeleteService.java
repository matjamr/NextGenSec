package com.sec.gen.next.serviceorchestrator.internal.product.service.user;

import com.next.gen.api.User;
import com.next.gen.sec.model.SensitiveDataModel;
import com.sec.gen.next.serviceorchestrator.common.templates.DeleteService;
import com.sec.gen.next.serviceorchestrator.internal.email.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.function.BiConsumer;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class UserProductDeleteService implements DeleteService<SensitiveDataModel, SensitiveDataModel> {

    private final BiConsumer<SensitiveDataModel, User> imagesBasedSensitiveDataRemovalConsumer;
    private final BiConsumer<SensitiveDataModel, User> idBasedSensitiveDataRemovalConsumer;
    private final Function<SensitiveDataModel, User> userFromSensitiveDataProvider;
    private final UserRepository userRepository;

    @Override
    public SensitiveDataModel delete(SensitiveDataModel sensitiveDataModel) {

        User user = userFromSensitiveDataProvider.apply(sensitiveDataModel);

        if(!CollectionUtils.isEmpty(sensitiveDataModel.getImages())) {
            imagesBasedSensitiveDataRemovalConsumer.accept(sensitiveDataModel, user);
        } else {
            idBasedSensitiveDataRemovalConsumer.accept(sensitiveDataModel, user);
        }

        userRepository.save(user);

        return sensitiveDataModel;
    }
}
