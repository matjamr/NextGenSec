package com.sec.gen.next.backend.internal.device.repository;

import com.sec.gen.next.backend.internal.api.internal.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {
}
