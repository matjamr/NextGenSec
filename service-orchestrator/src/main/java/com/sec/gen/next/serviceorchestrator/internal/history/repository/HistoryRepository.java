package com.sec.gen.next.serviceorchestrator.internal.history.repository;

import com.next.gen.api.HistoryEntrance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<HistoryEntrance, String> {
}
