package com.sec.gen.next.serviceorchestrator.internal.device.service;
import com.next.gen.sec.model.PlacesModel;
import com.next.gen.sec.model.Role;
import com.sec.gen.next.serviceorchestrator.api.CustomAuthentication;
import com.sec.gen.next.serviceorchestrator.common.templates.QueryService;


import com.next.gen.sec.model.DeviceModel;
import com.sec.gen.next.serviceorchestrator.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.sec.gen.next.serviceorchestrator.internal.device.mapper.DeviceMapper;
import com.sec.gen.next.serviceorchestrator.internal.device.repository.DeviceRepository;

import java.util.List;

import static com.sec.gen.next.serviceorchestrator.exception.Error.NO_PLACES_ID;
import static com.sec.gen.next.serviceorchestrator.exception.Error.UNAUTHORIZED;

@Component
@RequiredArgsConstructor
public class RetrieveDevicesService implements QueryService<DeviceModel, String>{

    private final QueryService<PlacesModel, String> placesQueryService;
    private final DeviceRepository deviceRepository;
    private final DeviceMapper deviceMapper;

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

}
