package com.sec.gen.next.backend.device.builder;

import com.next.gen.api.Device;
import com.sec.gen.next.backend.device.DeviceContext;
import com.sec.gen.next.backend.device.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
public class DeviceToDbBuilder implements Consumer<DeviceContext> {
    private final DeviceRepository deviceRepository;
    private final DeviceMapper deviceMapper;

    @Override
    public void accept(DeviceContext deviceContext) {
        Device device = deviceMapper.from(deviceContext.getDeviceModel());

        log.info("Saving device: " + device);
        device = deviceRepository.save(device);

        deviceContext.setDevice(device);
    }

}
