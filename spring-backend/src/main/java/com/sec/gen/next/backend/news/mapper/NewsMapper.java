package com.sec.gen.next.backend.news.mapper;

import com.sec.gen.next.backend.api.external.NewsModel;
import com.next.gen.api.News;

import java.util.List;

public interface NewsMapper {
    News from(NewsModel newsModel);
    NewsModel from(News news);
    List<NewsModel> from(List<News> news);
    List<News> fromModelList(List<NewsModel> news);
}
