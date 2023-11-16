package com.sec.gen.next.backend.device.config;

import com.sec.gen.next.backend.api.exception.RecoverableServiceException;
import com.sec.gen.next.backend.api.external.DeviceModel;
import com.sec.gen.next.backend.api.external.DeviceModel;
import com.sec.gen.next.backend.api.external.DeviceModel;
import com.sec.gen.next.backend.common.Dispatcher;
import com.sec.gen.next.backend.common.Service;
import com.sec.gen.next.backend.common.impl.SingleEntityService;
import com.sec.gen.next.backend.device.builder.DeviceMapper;
import com.sec.gen.next.backend.device.builder.DeviceToDbBuilder;
import com.sec.gen.next.backend.device.repository.DeviceRepository;
import com.sec.gen.next.backend.places.builder.RoutingEnum;
import com.sec.gen.next.backend.device.DeviceContext;
import com.sec.gen.next.backend.device.builder.DeviceDispatcher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.sec.gen.next.backend.places.builder.RoutingEnum.*;
import static com.sec.gen.next.backend.places.builder.RoutingEnum.DELETE;

@Configuration
public class DeviceBeansConfig {

    @Bean("deviceDispatcher")
    public Dispatcher<DeviceModel, DeviceContext, RoutingEnum> deviceDispatcher(
            @Qualifier("addDeviceService") Service<DeviceModel, DeviceContext> addService,
            @Qualifier("getDeviceService") Service<DeviceModel, DeviceContext> getService
    ) {
        return new DeviceDispatcher(Map.of(
                ADD, addService,
                GET, getService
        ));
    }

    @Bean("addDeviceService")
    public Service<DeviceModel, DeviceContext> addDeviceService(
            @Qualifier("addDeviceFlow") List<Consumer<DeviceContext>> addDeviceFlow,
            @Qualifier("defaultDeviceResultBuilder") Function<DeviceContext, DeviceModel> defaultDeviceResultBuilder,
            @Qualifier("recoverableDeviceActionConsumer") BiConsumer<DeviceContext, RecoverableServiceException> recoverableActionConsumer
    ) {
        return new SingleEntityService<>(List.of(),
                addDeviceFlow,
                defaultDeviceResultBuilder,
                recoverableActionConsumer);
    }

    @Bean("addDeviceFlow")
    public List<Consumer<DeviceContext>> addDeviceFlow(
            @Qualifier("deviceToDbBuilder") Consumer<DeviceContext> deviceToDbBuilder
    ) {
        return List.of(
                deviceToDbBuilder
        );
    }

    @Bean("deviceToDbBuilder")
    public Consumer<DeviceContext> deviceToDbBuilder(
            final DeviceRepository deviceRepository,
            final DeviceMapper deviceMapper
            ) {
        return new DeviceToDbBuilder(deviceRepository, deviceMapper);
    }

    @Bean("getDeviceService")
    public Service<DeviceModel, DeviceContext> getDeviceService(
            @Qualifier("defaultDeviceResultBuilder") Function<DeviceContext, DeviceModel> defaultDeviceResultBuilder,
            @Qualifier("recoverableDeviceActionConsumer") BiConsumer<DeviceContext, RecoverableServiceException> recoverableActionConsumer
    ) {
        return new SingleEntityService<>(List.of(), List.of(), defaultDeviceResultBuilder, recoverableActionConsumer);
    }

    @Bean("recoverableDeviceActionConsumer")
    public BiConsumer<DeviceContext, RecoverableServiceException> recoverableActionConsumer() {
        return (context, error) -> context.getErrors().add(error.getError());
    }

    @Bean("defaultDeviceResultBuilder")
    public Function<DeviceContext, DeviceModel> defaultDeviceResultBuilder(
            final DeviceMapper deviceMapper
    ) {
        return deviceContext -> deviceMapper.from(deviceContext.getDevice());
    }
}
