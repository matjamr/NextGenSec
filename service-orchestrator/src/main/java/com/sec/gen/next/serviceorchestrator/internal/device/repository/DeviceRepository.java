package com.sec.gen.next.serviceorchestrator.internal.device.repository;

import com.next.gen.api.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, String> {
}
