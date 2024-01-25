package com.sec.gen.next.backend.internal.history.repository;

import com.sec.gen.next.backend.internal.api.internal.HistoryEntrance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<HistoryEntrance, Integer> {
}
