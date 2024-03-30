package com.sec.gen.next.backend.method.repository;

import com.sec.gen.next.backend.api.internal.Method;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MethodRepository extends JpaRepository<Method, Integer> {
}
