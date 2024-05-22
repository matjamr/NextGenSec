package com.sec.gen.next.serviceorchestrator.internal.device;

import com.next.gen.sec.model.DeviceModel;
import com.next.gen.sec.model.HistoryEntranceModel;
import com.sec.gen.next.serviceorchestrator.common.templates.CrudService;
import com.sec.gen.next.serviceorchestrator.common.templates.QueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/device")
@RequiredArgsConstructor
public class DeviceController {

    private final CrudService<DeviceModel, DeviceModel, String> deviceCrudService;
    private final QueryService<DeviceModel, String> retrieveDevicesService;

    @PostMapping
    public DeviceModel addDevice(@RequestBody DeviceModel deviceModel) {
        return deviceCrudService.save(deviceModel);
    }

    @GetMapping
    public List<DeviceModel> getDevices() {
        return retrieveDevicesService.findAll();
    }

    @GetMapping("{id}")
    public DeviceModel getDeviceById(@PathVariable String id) {
        return deviceCrudService.findBy(id);
    }
}
