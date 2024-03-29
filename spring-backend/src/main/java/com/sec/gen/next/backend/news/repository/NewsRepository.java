package com.sec.gen.next.backend.news.repository;

import com.sec.gen.next.backend.api.internal.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {
}
