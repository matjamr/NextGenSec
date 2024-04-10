package com.sec.gen.next.serviceorchestrator.news;

import com.next.gen.sec.model.NewsModel;
import com.sec.gen.next.serviceorchestrator.common.templates.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {
    private final CrudService<NewsModel, NewsModel, String> crudNewsService;

    @PostMapping
    public NewsModel save(@RequestBody NewsModel newsModel) {
        return crudNewsService.save(newsModel);
    }

    @PutMapping
    public NewsModel update(@RequestBody NewsModel newsModel) {
        return crudNewsService.update(newsModel);
    }

    @GetMapping
    public List<NewsModel> getAll() {
        return crudNewsService.findAll();
    }
}
