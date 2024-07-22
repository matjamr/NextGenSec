package com.sec.gen.next.serviceorchestrator.internal.places.mapper;

import com.next.gen.api.Webhook;
import com.next.gen.sec.model.WebhookModel;
import org.mapstruct.Mapper;

@Mapper
public interface WebhookMapper {
    Webhook map(WebhookModel webhookModel);
    WebhookModel map(Webhook webhook);
}
