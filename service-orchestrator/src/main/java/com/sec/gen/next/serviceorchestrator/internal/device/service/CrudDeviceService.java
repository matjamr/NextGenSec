package com.sec.gen.next.serviceorchestrator.internal.device.service;

import com.next.gen.api.custom.BetterOptional;
import com.next.gen.sec.model.DeviceModel;
import com.next.gen.sec.model.PlacesModel;
import com.next.gen.sec.model.ProductModel;
import com.next.gen.sec.model.Role;
import com.sec.gen.next.serviceorchestrator.api.CustomAuthentication;
import com.sec.gen.next.serviceorchestrator.common.templates.CrudService;
import com.sec.gen.next.serviceorchestrator.common.templates.SimpleQueryService;
import com.sec.gen.next.serviceorchestrator.exception.ServiceException;
import com.sec.gen.next.serviceorchestrator.internal.device.mapper.DeviceMapper;
import com.sec.gen.next.serviceorchestrator.internal.device.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import com.sec.gen.next.serviceorchestrator.common.templates.QueryService;

import java.util.List;
import java.util.function.Predicate;

import static com.sec.gen.next.serviceorchestrator.exception.Error.*;

@RequiredArgsConstructor
public class CrudDeviceService implements CrudService<DeviceModel, DeviceModel, String> {

    private final DeviceRepository deviceRepository;
    private final DeviceMapper deviceMapper;
    private final SimpleQueryService<String, ProductModel> productSimpleQueryService;
    private final SimpleQueryService<String, PlacesModel> placesSimpleQueryService;
    private final QueryService<PlacesModel, String> placesQueryService;


    @Override
    public List<DeviceModel> findAll() {
        CustomAuthentication user = (CustomAuthentication) SecurityContextHolder.getContext().getAuthentication();

        if(Role.USER.equals(user.getRole())) {
            throw new ServiceException(UNAUTHORIZED);
        }

        return filterDevices(deviceRepository.findAll().stream()
                .map(deviceMapper::map)
                .toList());
    }

    private List<DeviceModel> filterDevices(List<DeviceModel> deviceModels) {
        CustomAuthentication user = (CustomAuthentication) SecurityContextHolder.getContext().getAuthentication();

        if(Role.SYSTEM.equals(user.getRole())) {
            return deviceModels;
        }

        var place = placesQueryService.findAll()
                .stream()
                .findFirst()
                .orElseThrow(NO_PLACES_ID::getError);

        return deviceModels.stream()
                .filter(deviceModel -> deviceModel.getPlace().getId().equals(place.getId()))
                .toList();
    }

    @Override
    public DeviceModel save(DeviceModel deviceModel) {
        return BetterOptional.of(deviceModel)
                .externalCheck(() -> productSimpleQueryService.findBy(deviceModel.getProduct().getId()), NO_PLACES_ID.getError())
                .externalCheck(() -> placesSimpleQueryService.findBy(deviceModel.getPlace().getId()), NO_PLACES_ID.getError())
                .optionalMap(deviceMapper::map)
                .map(deviceRepository::save)
                .map(deviceMapper::map)
                .orElseThrow(INVALID_DEVICE_DATA::getError);
    }

    @Override
    public DeviceModel findBy(String id) {
        return deviceRepository.findById(id)
                .map(deviceMapper::map)
                .orElseThrow(INVALID_DEVICE_DATA::getError);
    }
}
