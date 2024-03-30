package com.sec.gen.next.backend.device;

import com.sec.gen.next.backend.api.external.DeviceModel;
import com.sec.gen.next.backend.common.Dispatcher;
import com.sec.gen.next.backend.places.builder.RoutingEnum;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/device")
public class DeviceController {

    private final Dispatcher<DeviceModel, DeviceContext, RoutingEnum> deviceDispatcher;

    public DeviceController(
            @Qualifier("deviceDispatcher") Dispatcher<DeviceModel, DeviceContext, RoutingEnum> deviceDispatcher) {
        this.deviceDispatcher = deviceDispatcher;
    }


    @PostMapping
    public DeviceModel addDevice(@RequestBody DeviceModel deviceModel) {
        return deviceDispatcher.dispatch(DeviceContext.builder().deviceModel(deviceModel).build(),
                RoutingEnum.ADD);
    }

    @GetMapping
    public DeviceModel getDevices(@RequestBody DeviceModel deviceModel) {
        return deviceDispatcher.dispatch(DeviceContext.builder().deviceModel(deviceModel).build(),
                RoutingEnum.ADD);
    }
}
