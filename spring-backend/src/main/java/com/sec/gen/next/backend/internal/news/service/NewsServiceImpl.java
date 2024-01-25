package com.sec.gen.next.backend.internal.news.service;

import com.sec.gen.next.backend.internal.api.exception.ServiceException;
import com.sec.gen.next.backend.internal.api.external.NewsModel;
import com.sec.gen.next.backend.internal.api.internal.Image;
import com.sec.gen.next.backend.internal.api.internal.News;
import com.sec.gen.next.backend.internal.image.repository.ImageRepository;
import com.sec.gen.next.backend.internal.news.mapper.NewsMapper;
import com.sec.gen.next.backend.internal.news.repository.NewsRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.sec.gen.next.backend.internal.api.exception.Error.NEWS_ERROR;

@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    private final ImageRepository imageRepository;

    @Override
    public NewsModel add(NewsModel newsModel) {
        Image image = imageRepository.getReferenceById(newsModel.getImageId());
        News news = newsMapper.from(newsModel)
                .setImage(image)
                .setLastUpdate(LocalDateTime.now());

        return Optional.of(newsRepository.save(news))
                .map(newsMapper::from)
                .orElseThrow(() -> new ServiceException(NEWS_ERROR));
    }

    @Override
    public NewsModel update(NewsModel newsModel) {
        return null;
    }

    @Override
    public List<NewsModel> get(Integer numberOfNews) {
        return Optional.ofNullable(numberOfNews)
                .map(num -> newsRepository.findAll().stream()
                        .sorted((o1, o2) -> o2.getLastUpdate().compareTo(o1.getLastUpdate()))
                        .limit(num)
                        .toList())
                .map(newsMapper::from)
                .orElse(newsMapper.from(newsRepository.findAll()));
    }
}
