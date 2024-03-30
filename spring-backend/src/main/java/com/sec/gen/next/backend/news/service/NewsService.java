package com.sec.gen.next.backend.news.service;

import com.sec.gen.next.backend.api.external.NewsModel;

import java.util.List;

public interface NewsService {
    NewsModel add(NewsModel newsModel);
    NewsModel update(NewsModel newsModel);

    List<NewsModel> get(Integer numberOfNews);
}
