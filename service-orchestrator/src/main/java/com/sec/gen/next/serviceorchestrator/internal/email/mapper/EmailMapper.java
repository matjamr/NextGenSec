package com.sec.gen.next.serviceorchestrator.internal.email.mapper;

import com.next.gen.api.Email;
import com.next.gen.sec.model.MailModel;
import com.sec.gen.next.serviceorchestrator.internal.places.mapper.UserMapper;
import org.mapstruct.Mapper;

@Mapper(uses = {UserMapper.class})
public interface EmailMapper {

    MailModel map(Email email);

    Email map(MailModel mailModel);
}
