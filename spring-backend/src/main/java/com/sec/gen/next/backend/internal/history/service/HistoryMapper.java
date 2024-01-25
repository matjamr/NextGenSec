package com.sec.gen.next.backend.internal.history.service;

import com.sec.gen.next.backend.internal.api.external.HistoryEntranceModel;
import com.sec.gen.next.backend.internal.api.internal.HistoryEntrance;
import com.sec.gen.next.backend.internal.product.mapper.ProductMapper;
import com.sec.gen.next.backend.internal.user.mapper.UserMapper;
import com.sec.gen.next.backend.internal.places.builder.PlacesMapper;
import org.mapstruct.Mapper;

@Mapper(uses = {UserMapper.class, ProductMapper.class, PlacesMapper.class})
public interface HistoryMapper {
    HistoryEntranceModel map(HistoryEntrance historyEntrance);
    HistoryEntrance map(HistoryEntranceModel historyEntranceModel);
}
