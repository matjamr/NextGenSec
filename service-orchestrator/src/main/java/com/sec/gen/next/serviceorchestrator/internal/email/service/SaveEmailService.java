package com.sec.gen.next.serviceorchestrator.internal.email.service;

import com.next.gen.api.Email;
import com.next.gen.api.Places;
import com.next.gen.api.User;
import com.next.gen.api.UserPlaceAssignment;
import com.next.gen.sec.model.MailModel;
import com.next.gen.sec.model.NotificationModel;
import com.next.gen.sec.model.OutboundEmailModel;
import com.next.gen.sec.model.Role;
import com.sec.gen.next.serviceorchestrator.common.util.ConsumerBasedFlowExecutor;
import com.sec.gen.next.serviceorchestrator.common.templates.SaveService;
import com.sec.gen.next.serviceorchestrator.external.kafka.KafkaProducer;
import com.sec.gen.next.serviceorchestrator.internal.email.mapper.EmailMapper;
import com.sec.gen.next.serviceorchestrator.internal.email.repository.EmailRepository;
import com.sec.gen.next.serviceorchestrator.internal.email.repository.UserRepository;
import com.sec.gen.next.serviceorchestrator.internal.places.mapper.UserMapper;
import com.sec.gen.next.serviceorchestrator.internal.places.repository.PlacesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class SaveEmailService implements Consumer<MailModel> {

    private final EmailMapper emailMapper;
    private final List<BiConsumer<Email, MailModel>> saveMailFlowConsumers;
    private final Consumer<MailModel> senderProvider;

    @Override
    public void accept(MailModel mailModel) {
        senderProvider.accept(mailModel);
        Email email = emailMapper.map(mailModel);
        ConsumerBasedFlowExecutor.execute(saveMailFlowConsumers, email, mailModel);
    }
}
