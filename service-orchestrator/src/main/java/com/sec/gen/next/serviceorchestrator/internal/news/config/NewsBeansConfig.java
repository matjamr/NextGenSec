package com.sec.gen.next.serviceorchestrator.internal.news.config;

import com.next.gen.sec.model.NewsModel;
import com.sec.gen.next.serviceorchestrator.common.templates.CrudService;
import com.sec.gen.next.serviceorchestrator.internal.news.repository.NewsRepository;
import com.sec.gen.next.serviceorchestrator.internal.news.mapper.NewsMapper;
import com.sec.gen.next.serviceorchestrator.internal.news.service.CrudNewsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NewsBeansConfig {

    @Bean("newsService")
    public CrudService<NewsModel, NewsModel, String> crudNewsService(
            final NewsMapper newsMapper,
            final NewsRepository newsRepository
            ) {
        return new CrudNewsService(newsRepository, newsMapper);
    }
}
