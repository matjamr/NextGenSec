package com.sec.gen.next.backend.internal.device.builder;

import com.sec.gen.next.backend.internal.api.exception.Error;
import com.sec.gen.next.backend.internal.api.exception.ServiceException;
import com.sec.gen.next.backend.internal.api.external.DeviceModel;
import com.sec.gen.next.backend.internal.common.Dispatcher;
import com.sec.gen.next.backend.internal.common.Service;
import com.sec.gen.next.backend.internal.device.DeviceContext;

import com.sec.gen.next.backend.internal.places.builder.RoutingEnum;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Optional;


@RequiredArgsConstructor
public class DeviceDispatcher implements Dispatcher<DeviceModel, DeviceContext, RoutingEnum> {

    private final Map<RoutingEnum, Service<DeviceModel, DeviceContext>> servicesActionMap;

    @Override
    public DeviceModel dispatch(DeviceContext deviceContext, RoutingEnum enumRoute) {
        return Optional.ofNullable(servicesActionMap.get(enumRoute))
                .map(service -> {
                    service.validate(deviceContext);
                    return service;
                })
                .map(service -> service.doService(deviceContext))
                .orElseThrow(() -> new ServiceException(Error.NOT_SUPPORTED_DISPATCHER_METHOD));
    }
}
