package com.sec.next.gen.userservice.service.internal.authorization.client;

import com.next.gen.sec.model.RegistrationSource;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static com.sec.next.gen.userservice.config.Error.CLIENT_NOT_SUPPORTED;

@RequiredArgsConstructor
public class ExternalClientProvider implements Function<RegistrationSource, ExternalClient> {

    private final Map<RegistrationSource, ExternalClient> externalClientMap;

    @Override
    public ExternalClient apply(RegistrationSource registrationSource) {
        return Optional.ofNullable(externalClientMap.get(registrationSource))
                .orElseThrow(CLIENT_NOT_SUPPORTED::getError);
    }




}
