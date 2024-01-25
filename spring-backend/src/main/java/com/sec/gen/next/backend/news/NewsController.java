package com.sec.gen.next.backend.news;

import com.sec.gen.next.backend.api.external.NewsModel;
import com.sec.gen.next.backend.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @PostMapping
    public NewsModel add(@RequestBody NewsModel newsModel) {
        return newsService.add(newsModel);
    }

    @PutMapping
    public NewsModel update(@RequestBody NewsModel newsModel) {
        return newsService.update(newsModel);
    }

    @GetMapping
    public List<NewsModel> getAll(@RequestParam(required = false) Integer numberOfNews) {
        return newsService.get(numberOfNews);
    }
}
