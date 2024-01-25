package com.sec.gen.next.backend.internal.history;

import com.sec.gen.next.backend.internal.api.external.HistoryEntranceModel;
import com.sec.gen.next.backend.internal.api.internal.ClaimsUser;
import com.sec.gen.next.backend.internal.history.service.HistoryService;
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
    public List<HistoryEntranceModel> getHistory(ServletRequest servletRequest) {
        return historyService.getHistoryEntranceForUser((ClaimsUser) servletRequest.getAttribute("PRINCIPAL"));
    }

    @PostMapping
    public HistoryEntranceModel addEntrance(@RequestBody HistoryEntranceModel historyEntranceModel) {
        return historyService.addHistoryEntrance(historyEntranceModel);
    }
}
