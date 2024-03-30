package com.sec.gen.next.backend.news.config;

import com.sec.gen.next.backend.image.repository.ImageRepository;
import com.sec.gen.next.backend.news.mapper.NewsMapper;
import com.sec.gen.next.backend.news.mapper.NewsMapperImpl;
import com.sec.gen.next.backend.news.service.NewsService;
import com.sec.gen.next.backend.news.service.NewsServiceImpl;
import com.sec.gen.next.backend.news.repository.NewsRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NewsBeansConfig {

    @Bean("newsMapper")
    public NewsMapper newsMapper() {
        return new NewsMapperImpl();
    }

    @Bean("newsService")
    public NewsService newsService(
            final NewsMapper newsMapper,
            final NewsRepository newsRepository,
            final ImageRepository imageRepository
            ) {
        return new NewsServiceImpl(newsRepository, newsMapper, imageRepository);
    }
}
