package com.sec.gen.next.serviceorchestrator.internal.email.service.save;

import com.next.gen.api.Email;
import com.next.gen.api.User;
import com.next.gen.api.security.CustomAuthentication;
import com.next.gen.sec.model.MailModel;
import com.next.gen.sec.model.UserModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Component
public class SenderProvider implements BiConsumer<Email, MailModel> {

    @Override
    public void accept(Email email, MailModel mailModel) {
        CustomAuthentication user = (CustomAuthentication) SecurityContextHolder.getContext().getAuthentication();

        email.setFrom(new User()
                .setId(user.getId())
                .setEmail(user.getEmail()));
    }
}
