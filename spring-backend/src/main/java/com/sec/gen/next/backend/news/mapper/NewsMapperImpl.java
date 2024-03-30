package com.sec.gen.next.backend.news.mapper;

import com.sec.gen.next.backend.api.external.NewsModel;
import com.sec.gen.next.backend.api.internal.News;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public class NewsMapperImpl implements NewsMapper {

    @Value("${server.url}")
    private String serverUrl;

    @Override
    public News from(NewsModel newsModel) {
        return new News()
                .setTitle(newsModel.getTitle())
                .setDescription(newsModel.getDescription())
                .setId(newsModel.getId())
                .setLastUpdate(newsModel.getLastUpdate());
    }

    @Override
    public NewsModel from(News news) {
        return NewsModel.builder()
                .id(news.getId())
                .description(news.getDescription())
                .lastUpdate(news.getLastUpdate())
                .imageId(news.getImage().getId())
                .title(news.getTitle())
                .imageUrl(serverUrl + "/api/image/" + news.getImage().getId())
                .build();
    }

    @Override
    public List<NewsModel> from(List<News> news) {
        return news.stream()
                .map(this::from)
                .toList();
    }

    @Override
    public List<News> fromModelList(List<NewsModel> news) {
        return news.stream()
                .map(this::from)
                .toList();
    }
}
