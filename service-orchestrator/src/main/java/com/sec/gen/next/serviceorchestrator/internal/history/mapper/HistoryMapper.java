package com.sec.gen.next.serviceorchestrator.internal.history.mapper;

import com.next.gen.api.HistoryEntrance;
import com.next.gen.sec.model.HistoryEntranceModel;
import com.sec.gen.next.serviceorchestrator.internal.places.mapper.PlacesMapper;
import com.sec.gen.next.serviceorchestrator.internal.places.mapper.UserMapper;
import com.sec.gen.next.serviceorchestrator.internal.product.mapper.ProductMapper;
import org.mapstruct.Mapper;

@Mapper(uses = {UserMapper.class, ProductMapper.class, PlacesMapper.class})
public interface HistoryMapper {
    HistoryEntranceModel map(HistoryEntrance historyEntrance);
    HistoryEntrance map(HistoryEntranceModel historyEntranceModel);
}
