package com.sec.gen.next.backend.internal.history.service;

import com.sec.gen.next.backend.internal.api.external.HistoryEntranceModel;
import com.sec.gen.next.backend.internal.api.internal.ClaimsUser;

import java.util.List;

public interface HistoryService {
    List<HistoryEntranceModel> getHistoryEntranceForUser(ClaimsUser claimsUser);

    HistoryEntranceModel addHistoryEntrance(HistoryEntranceModel historyEntranceModel);
}
