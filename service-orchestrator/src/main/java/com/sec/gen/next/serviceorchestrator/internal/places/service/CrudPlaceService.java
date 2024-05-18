package com.sec.gen.next.serviceorchestrator.internal.places.service;

import com.next.gen.api.Places;
import com.next.gen.api.custom.BetterOptional;
import com.next.gen.sec.model.AddressModel;
import com.next.gen.sec.model.DeviceModel;
import com.next.gen.sec.model.PlacesModel;
import com.sec.gen.next.serviceorchestrator.common.templates.CrudService;
import com.sec.gen.next.serviceorchestrator.external.NominatimClient;
import com.sec.gen.next.serviceorchestrator.internal.places.mapper.PlacesMapper;
import com.sec.gen.next.serviceorchestrator.internal.places.repository.PlacesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static com.sec.gen.next.serviceorchestrator.exception.Error.*;
import static java.util.Objects.isNull;

@Slf4j
@RequiredArgsConstructor
public class CrudPlaceService implements CrudService<PlacesModel, PlacesModel, String> {
    private final PlacesRepository placesRepository;
    private final PlacesMapper placesMapper;
    private final CrudService<DeviceModel, DeviceModel, String> deviceCrudService;
    private final NominatimClient nominatimClient;
    private final Function<AddressModel, String> nominatimQueryBuilder;

    @Override
    public PlacesModel save(PlacesModel placesModel) {
        return BetterOptional.of(placesModel)
                .verify(() -> isNull(placesModel.getId()), PLACE_EXISTS.getError())
                .peek(this::additionallyMapAddress)
                .map(placesMapper::map)
                .map(placesRepository::save)
                .map(placesMapper::map)
                .orElseThrow(PLACE_EXISTS::getError);
    }

    private void additionallyMapAddress(PlacesModel places) {
        if(places.getAddress() != null) {
            var response = nominatimClient.getPlaceCoords(nominatimQueryBuilder.apply(places.getAddress()), "json")
                    .stream()
                    .findFirst()
                    .orElseThrow(INVALID_ADDRESS_DATA::getError);

            places.getAddress().latitude(response.getLat()).longitude(response.getLon());
        }
    }

    @Override
    public List<PlacesModel> findAll() {
        return placesRepository.findAll()
                .stream()
                .map(placesMapper::map)
                .toList();
    }

    @Override
    public PlacesModel delete(PlacesModel placesModel) {
        return BetterOptional.of(placesModel)
                .verify(() -> placesRepository.existsById(placesModel.getId()), NO_PLACES_ID.getError())
                .map(placesMapper::map)
                .peek(placesRepository::delete)
                .map(placesMapper::map)
                .orElseThrow(INVALID_PLACE_DATA::getError);
    }

    @Override
    public PlacesModel findBy(String s) {
        return placesRepository.findByPlaceName(s)
                .map(placesMapper::map)
                .map(placesModel -> placesModel.devices(deviceCrudService.findAll().stream()
                        .filter(device -> device.getPlace().getId().equals(placesModel.getId()))
                        .toList()))
                .orElseThrow(NO_PLACES_ID::getError);
    }

    @Override
    public PlacesModel update(PlacesModel placesModel) {
        return BetterOptional.of(placesModel)
                .verify(() -> placesRepository.existsById(placesModel.getId()), NO_PLACES_ID.getError())
                .map(placesMapper::map)
                .map(this::updateBaseValues)
                .map(placesRepository::save)
                .map(placesMapper::map)
                .toOptional().orElseThrow(INVALID_PLACE_DATA::getError);
    }

    private Places updateBaseValues(Places placesModel) {
        Places places = this.placesRepository.findByPlaceName(placesModel.getPlaceName())
                .orElseThrow(INVALID_PLACE_DATA::getError);

        Optional.ofNullable(placesModel.getPlaceName())
                .filter(StringUtils::isNotEmpty)
                .ifPresent(places::setPlaceName);

        Optional.ofNullable(placesModel.getEmailPlace())
                .filter(StringUtils::isNotEmpty)
                .ifPresent(places::setEmailPlace);

        Optional.ofNullable(placesModel.getDescription())
                .filter(StringUtils::isNotEmpty)
                .ifPresent(places::setDescription);

        Optional.ofNullable(placesModel.getAddress())
                .ifPresent(places::setAddress);

        return places;
    }
}
