package com.sec.gen.next.serviceorchestrator.internal.email.service;

import com.next.gen.api.Email;
import com.next.gen.sec.model.MailModel;
import com.next.gen.sec.model.MailRetrieveResponse;
import com.sec.gen.next.serviceorchestrator.api.CustomAuthentication;
import com.sec.gen.next.serviceorchestrator.internal.email.mapper.EmailMapper;
import com.sec.gen.next.serviceorchestrator.internal.email.repository.EmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class SimpleQueryEmailService implements Function<Pageable, Page<MailRetrieveResponse>> {

    private final EmailMapper emailMapper;
    private final EmailRepository emailRepository;

    @Override
    public Page<MailRetrieveResponse> apply(Pageable pageable) {
        CustomAuthentication user = (CustomAuthentication) SecurityContextHolder.getContext().getAuthentication();

        return new PageImpl<>(emailRepository.findAll(pageable)
                .filter(email -> email.getFrom().getEmail().equals(user.getEmail())
                        || email.getToUser().getEmail().equals(user.getEmail()))
                .map(emailMapper::mapTo)
                .toList());
    }
}
