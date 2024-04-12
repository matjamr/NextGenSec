package com.sec.gen.next.serviceorchestrator.history.mapper;

import com.next.gen.api.HistoryEntrance;
import com.next.gen.sec.model.HistoryEntranceModel;
import com.sec.gen.next.serviceorchestrator.places.mapper.PlacesMapper;
import com.sec.gen.next.serviceorchestrator.places.mapper.UserMapper;
import com.sec.gen.next.serviceorchestrator.product.mapper.ProductMapper;
import org.mapstruct.Mapper;

@Mapper(uses = {UserMapper.class, ProductMapper.class, PlacesMapper.class})
public interface HistoryMapper {
    HistoryEntranceModel map(HistoryEntrance historyEntrance);
    HistoryEntrance map(HistoryEntranceModel historyEntranceModel);
}
