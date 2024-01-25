package com.sec.gen.next.backend.internal.device.builder;

import com.sec.gen.next.backend.internal.api.external.DeviceModel;
import com.sec.gen.next.backend.internal.api.internal.Device;
import org.mapstruct.Mapper;

@Mapper
public interface DeviceMapper {
    DeviceModel from(Device device);
    Device from(DeviceModel deviceModel);
}
