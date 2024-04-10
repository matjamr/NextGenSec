package com.sec.gen.next.serviceorchestrator.places.config;

import com.next.gen.sec.model.PlacesModel;
import com.sec.gen.next.serviceorchestrator.common.templates.CrudService;
import com.sec.gen.next.serviceorchestrator.common.templates.SaveService;
import com.sec.gen.next.serviceorchestrator.common.templates.SimpleQueryService;
import com.sec.gen.next.serviceorchestrator.common.templates.UpdateService;
import com.sec.gen.next.serviceorchestrator.external.UserServiceClient;
import com.sec.gen.next.serviceorchestrator.places.mapper.PlacesMapper;
import com.sec.gen.next.serviceorchestrator.places.repository.PlacesRepository;
import com.sec.gen.next.serviceorchestrator.places.service.AddUserPlaceAssignmentService;
import com.sec.gen.next.serviceorchestrator.places.service.CrudPlaceService;
import com.sec.gen.next.serviceorchestrator.places.service.PlacesSaveService;
import com.sec.gen.next.serviceorchestrator.places.service.SimpleQueryPlacesService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


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
}
