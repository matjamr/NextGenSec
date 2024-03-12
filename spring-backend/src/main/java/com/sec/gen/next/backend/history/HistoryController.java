package com.sec.gen.next.backend.history;

import com.sec.gen.next.backend.api.external.HistoryEntranceModel;
import com.sec.gen.next.backend.history.service.HistoryService;
import jakarta.servlet.ServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping
    public List<HistoryEntranceModel> getHistory() {
        return historyService.getHistoryEntranceForUser();
    }

    @GetMapping("{placeId}")
    public List<HistoryEntranceModel> getAllHistory(@PathVariable Integer placeId) {
        return historyService.getHistoryEntrance(placeId);
    }

    @PostMapping
    public HistoryEntranceModel addEntrance(@RequestBody HistoryEntranceModel historyEntranceModel) {
        return historyService.addHistoryEntrance(historyEntranceModel);
    }
}
