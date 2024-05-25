package com.sec.gen.next.serviceorchestrator.internal.places.config;

import com.next.gen.api.Places;
import com.next.gen.sec.model.*;
import com.sec.gen.next.serviceorchestrator.common.templates.*;
import com.sec.gen.next.serviceorchestrator.external.UserServiceClient;
import com.sec.gen.next.serviceorchestrator.external.NominatimClient;
import com.sec.gen.next.serviceorchestrator.external.kafka.KafkaProducer;
import com.sec.gen.next.serviceorchestrator.internal.device.service.AddUserToPlaceService;
import com.sec.gen.next.serviceorchestrator.internal.device.service.ChangeUserToPlaceService;
import com.sec.gen.next.serviceorchestrator.internal.device.service.RemoveUserFromPlaceService;
import com.sec.gen.next.serviceorchestrator.internal.places.mapper.PlacesMapper;
import com.sec.gen.next.serviceorchestrator.internal.places.mapper.PlacesViewershipBuilder;
import com.sec.gen.next.serviceorchestrator.internal.places.mapper.UserPlaceAssignmentMapper;
import com.sec.gen.next.serviceorchestrator.internal.places.mapper.viewership.PlacesAdminViewershipBuilder;
import com.sec.gen.next.serviceorchestrator.internal.places.mapper.viewership.PlacesUserViewershipBuilder;
import com.sec.gen.next.serviceorchestrator.internal.places.repository.PlacesRepository;
import com.sec.gen.next.serviceorchestrator.internal.places.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;


@Configuration
public class BeansConfig {
    @Bean
    public CrudService<PlacesModel, PlacesModel, String> crudPlaceService(PlacesRepository placesRepository,
                                                                          PlacesMapper placesMapper,
                                                                          CrudService<DeviceModel, DeviceModel, String> deviceCrudService,
                                                                          NominatimClient nominatimClient,
                                                                          Function<AddressModel, String> nominatimQueryBuilder,
                                                                          PlacesRequestContext placesRequestContext) {
        return new CrudPlaceService(placesRepository, placesMapper, deviceCrudService, nominatimClient, nominatimQueryBuilder, placesRequestContext);
    }

    @Bean
    public UpdateService<PlacesModel, PlacesModel> addUserPlaceAssignmentService(@Qualifier("placeSaveService") SaveService<PlacesModel, PlacesModel> placesSaveService,
                                                                                 UserServiceClient userServiceClient,
                                                                                 SimpleQueryService<String, PlacesModel> simpleQueryPlacesService) {
        return new AddUserPlaceAssignmentService(userServiceClient, simpleQueryPlacesService, placesSaveService);
    }

    @Bean
    public SaveService<PlacesModel, PlacesModel> placeSaveService(PlacesRepository placesRepository,
                                                                  PlacesMapper placesMapper) {
        return new PlacesSaveService(placesRepository, placesMapper);
    }

    @Bean
    public SimpleQueryPlacesService simpleQueryPlacesService(PlacesRepository placesRepository,
                                                             PlacesMapper placesMapper) {
        return new SimpleQueryPlacesService(placesRepository, placesMapper);
    }

    @Bean
    public Supplier<List<PlacesModel>> placesForUserSupplier(
            final @Qualifier("crudPlaceService") ListQueryService<PlacesModel> placesListQueryService
            ) {
        return new PlacesForUserSupplier(placesListQueryService);
    }

    @Bean
    public DeleteService<List<String>, List<String>> placesDeleteService(PlacesRepository placesRepository) {
        return new PlacesDeleteService(placesRepository);
    }

    @Bean
    public UpdateService<ModifyUserToPlaceModel, PlacesModel> changeUserToPlaceService(
            final PlacesRepository placesRepository,
            final PlacesMapper placesMapper
    ) {
        return new ChangeUserToPlaceService(placesRepository, placesMapper);
    }

    @Bean
    public UpdateService<ModifyUserToPlaceModel, PlacesModel> addUserToPlaceService(
            final PlacesRepository placesRepository,
            final PlacesMapper placesMapper,
            final UserPlaceAssignmentMapper userPlaceAssignmentMapper,
            final SaveService<NotificationModel, NotificationModel> sendNotificationsService
            ) {
        return new AddUserToPlaceService(placesRepository, placesMapper,
                userPlaceAssignmentMapper, sendNotificationsService);
    }

    @Bean
    public UpdateService<ModifyUserToPlaceModel, PlacesModel> removeUserFromPlaceService(
            final PlacesRepository placesRepository,
            final PlacesMapper placesMapper,
            final SaveService<NotificationModel, NotificationModel> sendNotificationsService
    ) {
        return new RemoveUserFromPlaceService(placesRepository, placesMapper, sendNotificationsService);
    }

    @Bean
    @RequestScope
    public PlacesRequestContext placesRequestContext() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest httpServletRequest = sra.getRequest();

        String longitude = httpServletRequest.getParameter("longitude");
        String latitude = httpServletRequest.getParameter("latitude");
        String kmRange = httpServletRequest.getParameter("kmRange");

        return new PlacesRequestContext()
                .setLongitude(longitude != null ? Double.valueOf(longitude) : Double.valueOf(-1111))
                .setLatitude(latitude != null ? Double.valueOf(latitude) : Double.valueOf(-1111))
                .setKmRange(kmRange != null ? Double.valueOf(kmRange) : Double.valueOf(-1111));
    }

    @Bean
    public Function<List<PlacesModel>, List<PlacesModel>> placesViewershipBuilder() {
        return new PlacesViewershipBuilder<>(
                Map.of(
                        Role.ADMIN, new PlacesAdminViewershipBuilder(),
                        Role.USER, new PlacesUserViewershipBuilder()
                )
        );
    }
}
