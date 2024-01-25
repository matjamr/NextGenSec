package com.sec.gen.next.backend.internal.places.builder;

import com.sec.gen.next.backend.internal.api.exception.Error;
import com.sec.gen.next.backend.internal.api.exception.ServiceException;
import com.sec.gen.next.backend.internal.api.external.PlacesModel;
import com.sec.gen.next.backend.internal.places.PlacesContext;
import com.sec.gen.next.backend.internal.common.Service;
import com.sec.gen.next.backend.internal.common.Dispatcher;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@RequiredArgsConstructor
public class PlacesDispatcher implements Dispatcher<List<PlacesModel>, PlacesContext, RoutingEnum> {

    private final Map<RoutingEnum, Service<List<PlacesModel>, PlacesContext>> servicesActionMap;

    @Override
    public List<PlacesModel> dispatch(PlacesContext placesContext, RoutingEnum enumRoute) {
        return Optional.ofNullable(servicesActionMap.get(enumRoute))
                .map(service -> {
                    service.validate(placesContext);
                    return service;
                })
                .map(service -> service.doService(placesContext))
                .orElseThrow(() -> new ServiceException(Error.NOT_SUPPORTED_DISPATCHER_METHOD));
    }
}
