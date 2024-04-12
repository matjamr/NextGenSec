package com.sec.gen.next.serviceorchestrator.history.service;

import com.next.gen.api.AssignmentRole;
import com.next.gen.api.Places;
import com.next.gen.api.User;
import com.next.gen.api.UserPlaceAssignment;
import com.next.gen.sec.model.HistoryEntranceModel;
import com.sec.gen.next.serviceorchestrator.common.templates.SimpleQueryService;
import com.sec.gen.next.serviceorchestrator.history.mapper.HistoryMapper;
import com.sec.gen.next.serviceorchestrator.history.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class HistoryListQueryService implements SimpleQueryService<String, List<HistoryEntranceModel>> {

    private final HistoryRepository historyRepository;
    private final HistoryMapper historyMapper;

    @Override
    public List<HistoryEntranceModel> findBy(String id) {
        return historyRepository.findAll()
                .stream()
                .filter(historyEntrance -> historyEntrance.getPlaces().getId().equals(id))
                .map(historyMapper::map)
                .toList();
    }
}
