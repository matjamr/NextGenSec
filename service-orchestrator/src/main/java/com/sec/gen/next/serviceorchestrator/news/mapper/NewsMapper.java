package com.sec.gen.next.serviceorchestrator.news.mapper;

import com.next.gen.api.News;
import com.next.gen.sec.model.NewsModel;
import com.sec.gen.next.serviceorchestrator.image.mapper.ImageMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = ImageMapper.class)
public interface NewsMapper {
    News map(NewsModel newsModel);
    NewsModel map(News news);
    List<NewsModel> mapCollection(List<News> news);
    List<News> map(List<NewsModel> news);
}
