package com.sec.gen.next.serviceorchestrator.internal.product.service;

import com.next.gen.api.State;
import com.next.gen.api.User;
import com.next.gen.sec.model.SensitiveDataModel;
import com.sec.gen.next.serviceorchestrator.common.templates.ModifyService;
import com.sec.gen.next.serviceorchestrator.internal.email.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ModifySensitiveDataService implements ModifyService<SensitiveDataModel> {

    private final UserRepository userRepository;
    private final Function<SensitiveDataModel, User> userFromSensitiveDataProvider;

    @Override
    public void update(SensitiveDataModel sensitiveDataModel) {
        User userFromDb = userFromSensitiveDataProvider.apply(sensitiveDataModel);

        Optional.ofNullable(sensitiveDataModel.getState())
                .ifPresent(state -> userFromDb.getSensitiveData().forEach(data -> data.setState(State.valueOf(state))));

        userRepository.save(userFromDb);
    }

}
