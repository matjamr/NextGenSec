package com.sec.gen.next.serviceorchestrator.internal.device.service;

import com.next.gen.api.custom.BetterOptional;
import com.next.gen.sec.model.DeviceModel;
import com.next.gen.sec.model.PlacesModel;
import com.next.gen.sec.model.ProductModel;
import com.sec.gen.next.serviceorchestrator.common.templates.CrudService;
import com.sec.gen.next.serviceorchestrator.common.templates.SimpleQueryService;
import com.sec.gen.next.serviceorchestrator.internal.device.mapper.DeviceMapper;
import com.sec.gen.next.serviceorchestrator.internal.device.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Predicate;

import static com.sec.gen.next.serviceorchestrator.exception.Error.INVALID_DEVICE_DATA;
import static com.sec.gen.next.serviceorchestrator.exception.Error.NO_PLACES_ID;

@RequiredArgsConstructor
public class CrudDeviceService implements CrudService<DeviceModel, DeviceModel, String> {

    private final DeviceRepository deviceRepository;
    private final DeviceMapper deviceMapper;
    private final SimpleQueryService<String, ProductModel> productSimpleQueryService;
    private final SimpleQueryService<String, PlacesModel> placesSimpleQueryService;

    @Override
    public List<DeviceModel> findAll() {
        return deviceRepository.findAll().stream()
                .map(deviceMapper::map)
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
