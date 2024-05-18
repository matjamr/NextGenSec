package com.sec.gen.next.serviceorchestrator.internal.places.config;

import com.next.gen.sec.model.AddressModel;
import com.next.gen.sec.model.DeviceModel;
import com.next.gen.sec.model.ModifyUserToPlaceModel;
import com.next.gen.sec.model.PlacesModel;
import com.sec.gen.next.serviceorchestrator.common.templates.*;
import com.sec.gen.next.serviceorchestrator.external.UserServiceClient;
import com.sec.gen.next.serviceorchestrator.external.NominatimClient;
import com.sec.gen.next.serviceorchestrator.internal.device.service.AddUserToPlaceService;
import com.sec.gen.next.serviceorchestrator.internal.device.service.ChangeUserToPlaceService;
import com.sec.gen.next.serviceorchestrator.internal.device.service.RemoveUserFromPlaceService;
import com.sec.gen.next.serviceorchestrator.internal.places.mapper.PlacesMapper;
import com.sec.gen.next.serviceorchestrator.internal.places.mapper.UserPlaceAssignmentMapper;
import com.sec.gen.next.serviceorchestrator.internal.places.repository.PlacesRepository;
import com.sec.gen.next.serviceorchestrator.internal.places.service.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;


@Configuration
public class BeansConfig {
    @Bean
    public CrudService<PlacesModel, PlacesModel, String> crudPlaceService(PlacesRepository placesRepository,
                                                                          PlacesMapper placesMapper,
                                                                          CrudService<DeviceModel, DeviceModel, String> deviceCrudService,
                                                                          NominatimClient nominatimClient,
                                                                          Function<AddressModel, String> nominatimQueryBuilder) {
        return new CrudPlaceService(placesRepository, placesMapper, deviceCrudService, nominatimClient, nominatimQueryBuilder);
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
    public Supplier<PlacesModel> placesForUserSupplier(
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
            final UserPlaceAssignmentMapper userPlaceAssignmentMapper
            ) {
        return new AddUserToPlaceService(placesRepository, placesMapper, userPlaceAssignmentMapper);
    }

    @Bean
    public UpdateService<ModifyUserToPlaceModel, PlacesModel> removeUserFromPlaceService(
            final PlacesRepository placesRepository,
            final PlacesMapper placesMapper
    ) {
        return new RemoveUserFromPlaceService(placesRepository, placesMapper);
    }
}
