package com.sec.gen.next.backend.internal.device;

import com.sec.gen.next.backend.internal.api.exception.Error;
import com.sec.gen.next.backend.internal.api.external.DeviceModel;
import com.sec.gen.next.backend.internal.api.internal.Device;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder(toBuilder = true)
@Data
public class DeviceContext {
    private DeviceModel deviceModel;
    private List<DeviceModel> deviceModelList;
    private Device device;
    private List<Error> errors;
}
