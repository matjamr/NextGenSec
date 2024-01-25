package com.sec.gen.next.backend.history.service;

import com.sec.gen.next.backend.api.external.HistoryEntranceModel;

import java.util.List;

public interface HistoryService {
    List<HistoryEntranceModel> getHistoryEntranceForUser();

    HistoryEntranceModel addHistoryEntrance(HistoryEntranceModel historyEntranceModel);
}
