package com.sec.gen.next.serviceorchestrator.internal.email.service.save;

import com.next.gen.api.Email;
import com.next.gen.api.Places;
import com.next.gen.api.User;
import com.next.gen.api.UserPlaceAssignment;
import com.next.gen.sec.model.MailModel;
import com.next.gen.sec.model.OutboundEmailModel;
import com.next.gen.sec.model.Role;
import com.sec.gen.next.serviceorchestrator.external.kafka.KafkaProducer;
import com.sec.gen.next.serviceorchestrator.internal.email.repository.EmailRepository;
import com.sec.gen.next.serviceorchestrator.internal.email.repository.UserRepository;
import com.sec.gen.next.serviceorchestrator.internal.places.repository.PlacesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.function.BiConsumer;

@Component
@RequiredArgsConstructor
public class EmailNotifier implements BiConsumer<Email, MailModel> {

    private final KafkaProducer<OutboundEmailModel> outboundEmailModelKafkaProducer;

    @Override
    public void accept(Email email, MailModel mailModel) {
        mailModel.getTo().forEach(emailAddress -> outboundEmailModelKafkaProducer.sendMessage(new OutboundEmailModel()
                        .email(emailAddress)
                        .strategy("EMAIL_RECEIVED")
                        .subject(mailModel.getSubject())
                        .params(Map.of(
                                "emailFrom", emailAddress,
                                "content", mailModel.getContent(),
                                "subject", mailModel.getSubject()
                        ))));
    }
}
