package com.sec.gen.next.backend.history.service;

import com.sec.gen.next.backend.api.external.HistoryEntranceModel;
import com.sec.gen.next.backend.api.external.KafkaNotifModel;

import java.util.List;

public interface HistoryService {
    List<HistoryEntranceModel> getHistoryEntranceForUser();

    HistoryEntranceModel addHistoryEntrance(HistoryEntranceModel historyEntranceModel);

    void addHistoryEntrance(KafkaNotifModel kafkaNotifModel);

    List<HistoryEntranceModel> getHistoryEntrance(Integer placeName);
}
