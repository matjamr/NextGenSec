package com.sec.gen.next.serviceorchestrator.internal.product.repository;

import com.next.gen.api.SensitiveData;
import com.next.gen.api.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensitiveDataRepository extends JpaRepository<SensitiveData, String>, PagingAndSortingRepository<SensitiveData, String> {
    List<SensitiveData> findAllByUser(User user);
}
