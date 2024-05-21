package com.sec.gen.next.serviceorchestrator.internal.email.service;

import com.next.gen.sec.model.PlacesModel;
import com.sec.gen.next.serviceorchestrator.common.templates.QueryService;
import com.sec.gen.next.serviceorchestrator.internal.email.mapper.EmailMapper;
import com.sec.gen.next.serviceorchestrator.internal.email.repository.EmailRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class SimpleQueryEmailService implements QueryService<String, PlacesModel> {

    private final EmailMapper emailMapper;
    private final EmailRepository emailRepository;
}
