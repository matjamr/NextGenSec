package com.sec.gen.next.serviceorchestrator.internal.product.service.common;

import com.next.gen.api.User;
import com.next.gen.api.security.CustomAuthentication;
import com.next.gen.sec.model.Role;
import com.next.gen.sec.model.SensitiveDataModel;
import com.sec.gen.next.serviceorchestrator.internal.email.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.function.Function;

import static com.sec.gen.next.serviceorchestrator.exception.Error.INVALID_USER_DATA;
import static java.util.Objects.nonNull;

@Component
@RequiredArgsConstructor
public class UserFromSensitiveDataProvider implements Function<SensitiveDataModel, User> {

    private final UserRepository userRepository;

    @Override
    public User apply(SensitiveDataModel sensitiveDataModel) {
        CustomAuthentication user = (CustomAuthentication) SecurityContextHolder.getContext().getAuthentication();
        String email = user.getEmail();

        if(user.getRole().equals(Role.ROBOTIC) && nonNull(sensitiveDataModel.getUser().getEmail())) {
            email = sensitiveDataModel.getUser().getEmail();
        }

        return userRepository.findByEmail(email)
                .orElseThrow(INVALID_USER_DATA::getError);
    }
}
