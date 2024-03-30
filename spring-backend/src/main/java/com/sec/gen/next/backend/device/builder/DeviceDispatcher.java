package com.sec.gen.next.backend.device.builder;

import com.sec.gen.next.backend.api.exception.Error;
import com.sec.gen.next.backend.api.exception.ServiceException;
import com.sec.gen.next.backend.api.external.DeviceModel;
import com.sec.gen.next.backend.common.Dispatcher;
import com.sec.gen.next.backend.common.Service;
import com.sec.gen.next.backend.device.DeviceContext;

import com.sec.gen.next.backend.places.builder.RoutingEnum;
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
