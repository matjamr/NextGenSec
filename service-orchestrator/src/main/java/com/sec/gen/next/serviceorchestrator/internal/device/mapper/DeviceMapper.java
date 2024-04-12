package com.sec.gen.next.serviceorchestrator.internal.device.mapper;

import com.next.gen.api.Device;
import com.next.gen.sec.model.DeviceModel;
import org.mapstruct.Mapper;

@Mapper
public interface DeviceMapper {
    DeviceModel map(Device device);
    Device map(DeviceModel deviceModel);
}
