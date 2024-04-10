package com.sec.gen.next.serviceorchestrator.news.repository;

import com.next.gen.api.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, String> {
}
