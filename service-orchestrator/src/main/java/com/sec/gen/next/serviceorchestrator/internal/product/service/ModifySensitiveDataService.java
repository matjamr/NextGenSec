package com.sec.gen.next.serviceorchestrator.internal.product.service;

import com.next.gen.api.State;
import com.next.gen.api.User;
import com.next.gen.sec.model.Role;
import com.next.gen.sec.model.SensitiveDataModel;
import com.sec.gen.next.serviceorchestrator.api.CustomAuthentication;
import com.sec.gen.next.serviceorchestrator.common.templates.ModifyService;
import com.sec.gen.next.serviceorchestrator.internal.email.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.sec.gen.next.serviceorchestrator.exception.Error.INVALID_USER_DATA;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class ModifySensitiveDataService implements ModifyService<SensitiveDataModel> {

    private final UserRepository userRepository;

    @Override
    public void update(SensitiveDataModel sensitiveDataModel) {

        CustomAuthentication user = (CustomAuthentication) SecurityContextHolder.getContext().getAuthentication();
        String email = user.getEmail();

        if(user.getRole().equals(Role.ROBOTIC) && nonNull(sensitiveDataModel.getUser().getEmail())) {
            email = sensitiveDataModel.getUser().getEmail();
        }

        User userFromDb = userRepository.findByEmail(email)
                .orElseThrow(INVALID_USER_DATA::getError);

        Optional.ofNullable(sensitiveDataModel.getState())
                .ifPresent(state -> userFromDb.getSensitiveData().forEach(data -> data.setState(State.valueOf(state))));

        userRepository.save(userFromDb);
    }

}
