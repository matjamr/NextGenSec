package com.sec.gen.next.serviceorchestrator.internal.product.repository;

import com.next.gen.api.SensitiveData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensitiveDataRepository extends JpaRepository<SensitiveData, String> {
}
