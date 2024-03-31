package com.sec.gen.next.backend.device.builder;

import com.sec.gen.next.backend.api.external.DeviceModel;
import com.next.gen.api.Device;
import org.mapstruct.Mapper;

@Mapper
public interface DeviceMapper {
    DeviceModel from(Device device);
    Device from(DeviceModel deviceModel);
}
