package com.sec.gen.next.serviceorchestrator.internal.history.config;

import com.next.gen.api.security.UserServiceClient;
import com.next.gen.sec.model.*;
import com.sec.gen.next.serviceorchestrator.common.templates.CrudService;
import com.sec.gen.next.serviceorchestrator.common.templates.SaveService;
import com.sec.gen.next.serviceorchestrator.common.templates.SimpleQueryService;
import com.sec.gen.next.serviceorchestrator.external.kafka.KafkaProducer;
import com.sec.gen.next.serviceorchestrator.internal.history.mapper.HistoryMapper;
import com.sec.gen.next.serviceorchestrator.internal.history.repository.HistoryRepository;
import com.sec.gen.next.serviceorchestrator.internal.history.service.HistoryCrudService;
import com.sec.gen.next.serviceorchestrator.internal.history.service.HistoryListQueryService;
import com.sec.gen.next.serviceorchestrator.internal.history.service.KafkaHistoryConsumer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class HistoryBeansConfig {

    @Bean
    public KafkaHistoryConsumer kafkaHistoryConsumer(
            @Qualifier("crudPlaceService") final SimpleQueryService<String, PlacesModel> simpleQueryPlacesService,
            @Qualifier("deviceCrudService") final SimpleQueryService<String, DeviceModel> deviceQueryService,
            final UserServiceClient userServiceClient,
            final SaveService<HistoryEntranceModel, HistoryEntranceModel> historySaveService,
            final KafkaProducer<KafkaAsyncHistoryNotif> kafkaAsyncHistoryNotifKafkaProducer
    ) {
        return new KafkaHistoryConsumer(simpleQueryPlacesService, deviceQueryService,
                userServiceClient, historySaveService, kafkaAsyncHistoryNotifKafkaProducer);
    }

    @Bean
    public CrudService<HistoryEntranceModel, HistoryEntranceModel, String> historyCrudService(
            @Qualifier("crudPlaceService") final SimpleQueryService<String, PlacesModel> simpleQueryPlacesService,
            @Qualifier("productService") final SimpleQueryService<String, ProductModel> simpleQueryProductService, final UserServiceClient userServiceClient,
            final HistoryMapper historyMapper,
            final HistoryRepository historyRepository
            ) {
        return new HistoryCrudService(simpleQueryProductService, simpleQueryPlacesService,
                userServiceClient, historyMapper, historyRepository);
    }

    @Bean
    public SimpleQueryService<String, List<HistoryEntranceModel>> historyListQueryService(
            final HistoryRepository historyRepository,
            final HistoryMapper historyMapper
    ) {
        return new HistoryListQueryService(historyRepository, historyMapper);
    }

}
