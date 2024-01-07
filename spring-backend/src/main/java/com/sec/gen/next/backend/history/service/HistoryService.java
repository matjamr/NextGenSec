package com.sec.gen.next.backend.history.service;

import com.sec.gen.next.backend.api.external.HistoryEntranceModel;
import com.sec.gen.next.backend.api.internal.ClaimsUser;

import java.util.List;

public interface HistoryService {
    List<HistoryEntranceModel> getHistoryEntranceForUser(ClaimsUser claimsUser);

    HistoryEntranceModel addHistoryEntrance(HistoryEntranceModel historyEntranceModel);
}
