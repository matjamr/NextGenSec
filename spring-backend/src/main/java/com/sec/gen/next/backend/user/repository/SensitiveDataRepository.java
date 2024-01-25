package com.sec.gen.next.backend.user.repository;

import com.sec.gen.next.backend.api.internal.SensitiveData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensitiveDataRepository extends JpaRepository<SensitiveData, Integer> {
}
