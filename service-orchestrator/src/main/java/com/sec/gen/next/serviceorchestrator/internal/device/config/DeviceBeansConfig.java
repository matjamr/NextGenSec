package com.sec.gen.next.serviceorchestrator.internal.device.config;

import com.next.gen.sec.model.DeviceModel;
import com.next.gen.sec.model.PlacesModel;
import com.next.gen.sec.model.ProductModel;
import com.sec.gen.next.serviceorchestrator.common.templates.CrudService;
import com.sec.gen.next.serviceorchestrator.common.templates.SimpleQueryService;
import com.sec.gen.next.serviceorchestrator.internal.device.mapper.DeviceMapper;
import com.sec.gen.next.serviceorchestrator.internal.device.repository.DeviceRepository;
import com.sec.gen.next.serviceorchestrator.internal.device.service.CrudDeviceService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeviceBeansConfig {

    @Bean
    public CrudService<DeviceModel, DeviceModel, String> deviceCrudService(
            final DeviceRepository deviceRepository,
            final DeviceMapper deviceMapper,
            final SimpleQueryService<String, ProductModel> productSimpleQueryService,
            @Qualifier("simpleQueryPlacesService") final SimpleQueryService<String, PlacesModel> placesSimpleQueryService
            ) {
        return new CrudDeviceService(deviceRepository, deviceMapper,
                productSimpleQueryService, placesSimpleQueryService);
    }
}
