package com.sec.gen.next.backend.device;

import com.sec.gen.next.backend.api.exception.Error;
import com.sec.gen.next.backend.api.external.DeviceModel;
import com.next.gen.api.Device;
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
