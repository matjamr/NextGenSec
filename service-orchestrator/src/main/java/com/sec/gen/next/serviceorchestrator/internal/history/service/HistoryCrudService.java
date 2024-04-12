package com.sec.gen.next.serviceorchestrator.internal.history.service;

import com.next.gen.api.custom.BetterOptional;
import com.next.gen.sec.model.HistoryEntranceModel;
import com.next.gen.sec.model.PlacesModel;
import com.next.gen.sec.model.ProductModel;
import com.sec.gen.next.serviceorchestrator.common.templates.CrudService;
import com.sec.gen.next.serviceorchestrator.common.templates.SimpleQueryService;
import com.sec.gen.next.serviceorchestrator.external.user.UserServiceClient;
import com.sec.gen.next.serviceorchestrator.internal.history.mapper.HistoryMapper;
import com.sec.gen.next.serviceorchestrator.internal.history.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.OffsetDateTime;
import java.util.List;

import static com.sec.gen.next.serviceorchestrator.exception.Error.INVALID_HISTORY_ENTRANCE;

@RequiredArgsConstructor
public class HistoryCrudService implements CrudService<HistoryEntranceModel, HistoryEntranceModel, String> {

    private final SimpleQueryService<String, ProductModel> productSimpleQueryService;
    private final SimpleQueryService<String, PlacesModel> placesSimpleQueryService;
    private final UserServiceClient userServiceClient;
    private final HistoryMapper historyMapper;
    private final HistoryRepository historyRepository;

    @Override
    public HistoryEntranceModel save(HistoryEntranceModel historyEntranceModel) {
        return BetterOptional.of(historyEntranceModel)
                .externalCheck(() -> productSimpleQueryService.findBy(historyEntranceModel.getProduct().getId()), INVALID_HISTORY_ENTRANCE.getError())
                .externalCheck(() -> placesSimpleQueryService.findBy(historyEntranceModel.getPlaces().getId()), INVALID_HISTORY_ENTRANCE.getError())
                .externalCheck(() -> userServiceClient.getUserByEmail(historyEntranceModel.getUser().getEmail()), INVALID_HISTORY_ENTRANCE.getError())
                .optionalMap(historyMapper::map)
                .map(entry -> entry.setDate(OffsetDateTime.now()))
                .map(historyRepository::save)
                .map(historyMapper::map)
                .orElseThrow(INVALID_HISTORY_ENTRANCE::getError);
    }

    @Override
    public List<HistoryEntranceModel> findAll() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return historyRepository.findAll()
                .stream()
                .filter(historyEntrance -> historyEntrance.getUser().getEmail().equals(email))
                .map(historyMapper::map)
                .toList();
    }
}
