package com.sec.gen.next.backend.product.builder;

import com.sec.gen.next.backend.api.exception.Error;
import com.sec.gen.next.backend.api.exception.ServiceException;
import com.sec.gen.next.backend.api.external.ProductModel;
import com.sec.gen.next.backend.common.Dispatcher;
import com.sec.gen.next.backend.common.Service;
import com.sec.gen.next.backend.places.builder.RoutingEnum;
import com.sec.gen.next.backend.product.ProductContext;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@RequiredArgsConstructor
public class ProductDispatcher implements Dispatcher<List<ProductModel>, ProductContext, RoutingEnum> {

    private final Map<RoutingEnum, Service<List<ProductModel>, ProductContext>> servicesActionMap;

    @Override
    public List<ProductModel> dispatch(ProductContext productContext, RoutingEnum enumRoute) {
        return Optional.ofNullable(servicesActionMap.get(enumRoute))
                .map(service -> {
                    service.validate(productContext);
                    return service;
                })
                .map(service -> service.doService(productContext))
                .orElseThrow(() -> new ServiceException(Error.NOT_SUPPORTED_DISPATCHER_METHOD));
    }
}
