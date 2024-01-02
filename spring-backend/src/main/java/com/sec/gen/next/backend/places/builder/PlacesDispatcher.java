package com.sec.gen.next.backend.places.builder;

import com.sec.gen.next.backend.api.exception.Error;
import com.sec.gen.next.backend.api.exception.ServiceException;
import com.sec.gen.next.backend.api.external.PlacesModel;
import com.sec.gen.next.backend.places.PlacesContext;
import com.sec.gen.next.backend.common.Service;
import com.sec.gen.next.backend.common.Dispatcher;
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
