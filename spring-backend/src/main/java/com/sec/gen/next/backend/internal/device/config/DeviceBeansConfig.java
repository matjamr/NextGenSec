package com.sec.gen.next.backend.internal.device.config;

import com.sec.gen.next.backend.internal.api.exception.RecoverableServiceException;
import com.sec.gen.next.backend.internal.api.external.DeviceModel;
import com.sec.gen.next.backend.internal.common.Dispatcher;
import com.sec.gen.next.backend.internal.common.Service;
import com.sec.gen.next.backend.internal.common.impl.ServiceImpl;
import com.sec.gen.next.backend.internal.device.builder.DeviceMapper;
import com.sec.gen.next.backend.internal.device.builder.DeviceToDbBuilder;
import com.sec.gen.next.backend.internal.device.repository.DeviceRepository;
import com.sec.gen.next.backend.internal.device.DeviceContext;
import com.sec.gen.next.backend.internal.device.builder.DeviceDispatcher;
import com.sec.gen.next.backend.internal.places.builder.RoutingEnum;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.sec.gen.next.backend.internal.places.builder.RoutingEnum.*;

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
        return new ServiceImpl<>(List.of(),
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
        return new ServiceImpl<>(List.of(), List.of(), defaultDeviceResultBuilder, recoverableActionConsumer);
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
