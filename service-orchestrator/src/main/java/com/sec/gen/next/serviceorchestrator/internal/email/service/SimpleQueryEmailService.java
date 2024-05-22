package com.sec.gen.next.serviceorchestrator.internal.email.service;

import com.next.gen.api.Email;
import com.next.gen.sec.model.MailModel;
import com.sec.gen.next.serviceorchestrator.api.CustomAuthentication;
import com.sec.gen.next.serviceorchestrator.internal.email.mapper.EmailMapper;
import com.sec.gen.next.serviceorchestrator.internal.email.repository.EmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class SimpleQueryEmailService implements Supplier<List<Email>> {

    private final EmailMapper emailMapper;
    private final EmailRepository emailRepository;


    @Override
    public List<Email> get() {
        CustomAuthentication user = (CustomAuthentication) SecurityContextHolder.getContext().getAuthentication();
        return emailRepository.findAll()
                .stream()
                .filter(email -> email.getFrom().getEmail().equals(user.getEmail())
                        || email.getToUser().getEmail().equals(user.getEmail()))
                .toList();
    }
}
