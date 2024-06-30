package com.sec.gen.next.serviceorchestrator.internal.device;

import com.next.gen.sec.model.DeviceModel;
import com.sec.gen.next.serviceorchestrator.common.templates.CrudService;
import com.sec.gen.next.serviceorchestrator.common.templates.QueryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/device")
@RequiredArgsConstructor
public class DeviceController {

    private final CrudService<DeviceModel, DeviceModel, String> deviceCrudService;
    private final QueryService<DeviceModel, String> retrieveDevicesService;

    @Transactional
    @PostMapping
    public DeviceModel addDevice(@RequestBody DeviceModel deviceModel) {
        return deviceCrudService.save(deviceModel);
    }

    @Transactional
    @GetMapping
    public List<DeviceModel> getDevices() {
        return retrieveDevicesService.findAll();
    }

    @Transactional
    @GetMapping("{id}")
    public DeviceModel getDeviceById(@PathVariable String id) {
        return deviceCrudService.findBy(id);
    }
}
