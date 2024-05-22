package com.sec.gen.next.serviceorchestrator.internal.email.repository;

import com.next.gen.api.Email;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<Email, String> {
    Page<Email> findAll(Pageable pageable);
}
