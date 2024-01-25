package com.sec.gen.next.backend.internal.news.mapper;

import com.sec.gen.next.backend.internal.api.external.NewsModel;
import com.sec.gen.next.backend.internal.api.internal.News;

import java.util.List;

public interface NewsMapper {
    News from(NewsModel newsModel);
    NewsModel from(News news);
    List<NewsModel> from(List<News> news);
    List<News> fromModelList(List<NewsModel> news);
}
