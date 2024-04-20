package com.sec.gen.next.serviceorchestrator.internal.places.config;

import com.next.gen.sec.model.PlacesModel;
import com.sec.gen.next.serviceorchestrator.common.templates.*;
import com.sec.gen.next.serviceorchestrator.external.user.UserServiceClient;
import com.sec.gen.next.serviceorchestrator.internal.places.mapper.PlacesMapper;
import com.sec.gen.next.serviceorchestrator.internal.places.repository.PlacesRepository;
import com.sec.gen.next.serviceorchestrator.internal.places.service.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.function.Supplier;


@Configuration
public class BeansConfig {
    @Bean
    public CrudService<PlacesModel, PlacesModel, String> crudPlaceService(PlacesRepository placesRepository, PlacesMapper placesMapper) {
        return new CrudPlaceService(placesRepository, placesMapper);
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
}
