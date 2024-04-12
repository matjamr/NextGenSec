package com.sec.gen.next.serviceorchestrator.history;

import com.next.gen.sec.model.HistoryEntranceModel;
import com.sec.gen.next.serviceorchestrator.common.templates.CrudService;
import com.sec.gen.next.serviceorchestrator.common.templates.SimpleQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class HistoryController {

    private final CrudService<HistoryEntranceModel, HistoryEntranceModel, String> historyCrudService;
    private final SimpleQueryService<String, List<HistoryEntranceModel>> historyListQueryService;

    @GetMapping
    public List<HistoryEntranceModel> getHistory() {
        return historyCrudService.findAll();
    }

    @GetMapping("/{placeId}")
    public List<HistoryEntranceModel> getAllHistory(@PathVariable String placeId) {
        return historyListQueryService.findBy(placeId);
    }

    @PostMapping
    public HistoryEntranceModel addEntrance(@RequestBody HistoryEntranceModel historyEntranceModel) {
        return historyCrudService.save(historyEntranceModel);
    }
}
