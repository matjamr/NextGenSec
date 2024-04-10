package com.sec.gen.next.serviceorchestrator.places.mapper;

import com.next.gen.api.Device;
import com.next.gen.sec.model.DeviceModel;
import org.mapstruct.Mapper;

@Mapper
public interface DeviceMapper {
    Device map(DeviceModel deviceModel);
    DeviceModel map(Device device);
}
