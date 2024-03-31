package com.sec.gen.next.backend.method.repository;

import com.next.gen.api.Method;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MethodRepository extends JpaRepository<Method, Integer> {
}
