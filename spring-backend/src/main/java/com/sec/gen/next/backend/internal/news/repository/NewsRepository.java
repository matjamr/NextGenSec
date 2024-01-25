package com.sec.gen.next.backend.internal.news.repository;

import com.sec.gen.next.backend.internal.api.internal.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {
}
