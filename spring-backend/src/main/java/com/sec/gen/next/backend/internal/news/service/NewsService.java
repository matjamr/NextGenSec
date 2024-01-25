package com.sec.gen.next.backend.internal.news.service;

import com.sec.gen.next.backend.internal.api.external.NewsModel;

import java.util.List;

public interface NewsService {
    NewsModel add(NewsModel newsModel);
    NewsModel update(NewsModel newsModel);

    List<NewsModel> get(Integer numberOfNews);
}
