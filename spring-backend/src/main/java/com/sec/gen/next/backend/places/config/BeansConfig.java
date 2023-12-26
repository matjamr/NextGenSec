package com.sec.gen.next.backend.places.config;

import com.sec.gen.next.backend.api.exception.RecoverableServiceException;
import com.sec.gen.next.backend.api.external.AddressModel;
import com.sec.gen.next.backend.api.external.PlacesModel;
import com.sec.gen.next.backend.api.external.UserPlaceAssignmentModel;
import com.sec.gen.next.backend.api.internal.UserPlaceAssignment;
import com.sec.gen.next.backend.common.address.AddressMapper;
import com.sec.gen.next.backend.places.PlacesContext;
import com.sec.gen.next.backend.places.builder.PlacesDispatcher;
import com.sec.gen.next.backend.places.builder.PlacesMapper;
import com.sec.gen.next.backend.places.builder.RoutingEnum;
import com.sec.gen.next.backend.places.builder.add.PlacesToDbBuilder;
import com.sec.gen.next.backend.places.builder.common.DynamicStatusUpdater;
import com.sec.gen.next.backend.places.repository.PlacesRepository;
import com.sec.gen.next.backend.places.validator.PlaceAddressValidator;
import com.sec.gen.next.backend.places.validator.PlaceExistenceValidator;
import com.sec.gen.next.backend.common.Service;
import com.sec.gen.next.backend.common.address.AddressRepository;
import com.sec.gen.next.backend.common.address.AddressToDbBuilder;
import com.sec.gen.next.backend.common.address.validator.AddressValidator;
import com.sec.gen.next.backend.common.impl.ServiceImpl;
import com.sec.gen.next.backend.common.Dispatcher;
import com.sec.gen.next.backend.common.Validator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.sec.gen.next.backend.places.builder.RoutingEnum.*;

@Configuration
public class BeansConfig {

    @Bean("placesContext")
    @RequestScope
    public PlacesContext placesContext() {
        return PlacesContext.builder()
                .errors(List.of())
                .build();
    }

    @Bean("placesDispatcher")
    public Dispatcher<PlacesModel, PlacesContext, RoutingEnum> placesDispatcher(
            @Qualifier("addPlacesService") Service<PlacesModel, PlacesContext> addService,
            @Qualifier("updatePlacesService") Service<PlacesModel, PlacesContext> updateService,
            @Qualifier("getPlacesService") Service<PlacesModel, PlacesContext> getService,
            @Qualifier("deletePlacesService") Service<PlacesModel, PlacesContext> deleteService
    ) {
        return new PlacesDispatcher(Map.of(
                ADD, addService,
                UPDATE, updateService,
                GET, getService,
                DELETE, deleteService
        ));
    }

    @Bean("defaultPlacesResultBuilder")
    public Function<PlacesContext, PlacesModel> defaultPlacesResultBuilder() {
        return PlacesContext::getPlacesModel;
    }

    @Bean("recoverableActionConsumer")
    public BiConsumer<PlacesContext, RecoverableServiceException> recoverableActionConsumer() {
        return (context, error) -> context.getErrors().add(error.getError());
    }

    @Bean("addPlacesService")
    public Service<PlacesModel, PlacesContext> addPlacesService(
            @Qualifier("addPlacesValidators") List<Validator<PlacesContext>> addPlacesValidators,
            @Qualifier("addPlacesFlow") List<Consumer<PlacesContext>> addPlacesFlow,
            @Qualifier("defaultPlacesResultBuilder") Function<PlacesContext, PlacesModel> defaultPlacesResultBuilder,
            @Qualifier("recoverableActionConsumer") BiConsumer<PlacesContext, RecoverableServiceException> recoverableActionConsumer
    ) {
        return new ServiceImpl<>(addPlacesValidators,
                addPlacesFlow,
                defaultPlacesResultBuilder,
                recoverableActionConsumer);
    }

    @Bean("updatePlacesService")
    public Service<PlacesModel, PlacesContext> updatePlacesService(
            @Qualifier("defaultPlacesResultBuilder") Function<PlacesContext, PlacesModel> defaultPlacesResultBuilder,
            @Qualifier("recoverableActionConsumer") BiConsumer<PlacesContext, RecoverableServiceException> recoverableActionConsumer
    ) {
        return new ServiceImpl<>(List.of(), List.of(), defaultPlacesResultBuilder, recoverableActionConsumer);
    }

    @Bean("deletePlacesService")
    public Service<PlacesModel, PlacesContext> deletePlacesService(
            @Qualifier("defaultPlacesResultBuilder") Function<PlacesContext, PlacesModel> defaultPlacesResultBuilder,
            @Qualifier("recoverableActionConsumer") BiConsumer<PlacesContext, RecoverableServiceException> recoverableActionConsumer
    ) {
        return new ServiceImpl<>(List.of(), List.of(), defaultPlacesResultBuilder, recoverableActionConsumer);
    }

    @Bean("getPlacesService")
    public Service<PlacesModel, PlacesContext> getPlacesService(
            @Qualifier("defaultPlacesResultBuilder") Function<PlacesContext, PlacesModel> defaultPlacesResultBuilder,
            @Qualifier("recoverableActionConsumer") BiConsumer<PlacesContext, RecoverableServiceException> recoverableActionConsumer
    ) {
        return new ServiceImpl<>(List.of(), List.of(), defaultPlacesResultBuilder, recoverableActionConsumer);
    }

    @Bean("addPlacesValidators")
    public List<Validator<PlacesContext>> addPlacesValidators(
            @Qualifier("placeExistenceValidator") Validator<PlacesContext> placeExistenceValidator,
            @Qualifier("placeAddressValidator") Validator<PlacesContext> placeAddressValidator
    ) {
        return List.of(
                placeExistenceValidator,
                placeAddressValidator
        );
    }

    @Bean("addPlacesFlow")
    public List<Consumer<PlacesContext>> addPlacesConsumers(
            @Qualifier("placesToDbBuilder") Consumer<PlacesContext> placesToDbBuilder
    ) {
        return List.of(
                placesToDbBuilder
        );
    }

    @Bean("dynamicStatusUpdater")
    public Consumer<PlacesContext> dynamicStatusUpdater() {
        return new DynamicStatusUpdater();
    }

    @Bean("placesToDbBuilder")
    public Consumer<PlacesContext> placesToDbBuilder(
            PlacesRepository placesRepository,
            @Qualifier("dynamicStatusUpdater") Consumer<PlacesContext> dynamicStatusUpdater,
            @Qualifier("userPlaceAssignmentToDbBuilder") Function<List<UserPlaceAssignmentModel>, List<UserPlaceAssignment>> userPlaceAssignmentToDbBuilder,
            PlacesMapper placesMapper
    ) {
        return new PlacesToDbBuilder(placesRepository, userPlaceAssignmentToDbBuilder, dynamicStatusUpdater, placesMapper);
    }

    @Bean("placeExistenceValidator")
    public Validator<PlacesContext> placeExistenceValidator(
            PlacesRepository placesRepository
    ) {
        return new PlaceExistenceValidator(placesRepository);
    }

    @Bean("placeAddressValidator")
    public Validator<PlacesContext> placeAddressValidator(
            @Qualifier("addressValidator") Validator<AddressModel> addressValidator
    ) {
        return new PlaceAddressValidator(addressValidator);
    }

    @Bean("addressValidator")
    public Validator<AddressModel> addressValidator() {
        return new AddressValidator();
    }

    @Bean("addressToDbBuilder")
    public Consumer<AddressModel> addressToDbBuilder(
            AddressRepository addressRepository,
            AddressMapper addressMapper
    ) {
        return new AddressToDbBuilder(addressRepository, addressMapper);
    }
}
