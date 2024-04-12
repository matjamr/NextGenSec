package com.sec.gen.next.serviceorchestrator.internal.news.service;

import com.next.gen.sec.model.NewsModel;
import com.sec.gen.next.serviceorchestrator.common.templates.CrudService;
import com.sec.gen.next.serviceorchestrator.common.util.PlainModelUpdater;
import com.sec.gen.next.serviceorchestrator.internal.news.repository.NewsRepository;
import com.sec.gen.next.serviceorchestrator.internal.news.mapper.NewsMapper;
import lombok.RequiredArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static com.sec.gen.next.serviceorchestrator.exception.Error.INVALID_NEWS;


@RequiredArgsConstructor
public class CrudNewsService implements CrudService<NewsModel, NewsModel, String> {
    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;

    @Override
    public NewsModel save(NewsModel newsModel) {
        return Optional.ofNullable(newsModel)
                .map(newsMapper::map)
                .map(news -> news.setLastUpdate(OffsetDateTime.now()))
                .map(newsRepository::save)
                .map(newsMapper::map)
                .orElseThrow(INVALID_NEWS::getError);
    }

    @Override
    public NewsModel update(NewsModel newsModel) {
        return newsRepository.findById(newsModel.getId())
                .map(newsMapper::map)
                .map(oldNews -> PlainModelUpdater.plainUpdate(oldNews, newsModel))
                .map(newsMapper::map)
                .map(news -> news.setLastUpdate(OffsetDateTime.now()))
                .map(newsRepository::save)
                .map(newsMapper::map)
                .orElseThrow(INVALID_NEWS::getError);
    }

    @Override
    public List<NewsModel> findAll() {
        return newsRepository.findAll().stream()
                .sorted((o1, o2) -> o2.getLastUpdate().compareTo(o1.getLastUpdate()))
                .limit(5)
                .map(newsMapper::map)
                .toList();
    }
}
