package com.sec.gen.next.serviceorchestrator.internal.product.repository;

import com.next.gen.api.SensitiveData;
import com.next.gen.api.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensitiveDataRepository extends JpaRepository<SensitiveData, String>, PagingAndSortingRepository<SensitiveData, String> {
    Page<SensitiveData> findAllByUser(User user, Pageable pageable);
}
