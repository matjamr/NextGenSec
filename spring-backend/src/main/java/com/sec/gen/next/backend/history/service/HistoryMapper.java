package com.sec.gen.next.backend.history.service;

import com.sec.gen.next.backend.api.external.HistoryEntranceModel;
import com.sec.gen.next.backend.api.internal.HistoryEntrance;
import com.sec.gen.next.backend.places.builder.PlacesMapper;
import com.sec.gen.next.backend.product.mapper.ProductMapper;
import com.sec.gen.next.backend.user.mapper.UserMapper;
import org.mapstruct.Mapper;

@Mapper(uses = {UserMapper.class, ProductMapper.class, PlacesMapper.class})
public interface HistoryMapper {
    HistoryEntranceModel map(HistoryEntrance historyEntrance);
    HistoryEntrance map(HistoryEntranceModel historyEntranceModel);
}
