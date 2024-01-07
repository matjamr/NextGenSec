package com.sec.gen.next.backend.history.service;

import com.sec.gen.next.backend.api.exception.Error;
import com.sec.gen.next.backend.api.exception.ServiceException;
import com.sec.gen.next.backend.api.external.HistoryEntranceModel;
import com.sec.gen.next.backend.api.internal.*;
import com.sec.gen.next.backend.history.repository.HistoryRepository;
import com.sec.gen.next.backend.places.repository.PlacesRepository;
import com.sec.gen.next.backend.product.repository.ProductRepository;
import com.sec.gen.next.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final HistoryMapper historyMapper;
    private final HistoryRepository historyRepository;
    private final UserRepository userRepository;
    private final PlacesRepository placesRepository;
    private final ProductRepository productRepository;

    @Override
    public List<HistoryEntranceModel> getHistoryEntranceForUser(ClaimsUser claimsUser) {

        List<HistoryEntrance> historyEntrances = historyRepository.findAll();

        return historyEntrances.stream()
                .filter(historyEntrance -> historyEntrance.getUser().getEmail().equals(claimsUser.getEmail()))
                .map(historyMapper::map)
                .toList();
    }

    @Override
    public HistoryEntranceModel addHistoryEntrance(HistoryEntranceModel historyEntranceModel) {
        return historyMapper.map(
                historyRepository.save(
                    historyMapper.map(historyEntranceModel.setDate(LocalDateTime.now()))
                            .setProduct(getProductRepositoryById(historyEntranceModel))
                            .setUser(getUser(historyEntranceModel))
                            .setPlaces(getPlaces(historyEntranceModel))
                )
        );
    }

    private Places getPlaces(HistoryEntranceModel historyEntranceModel) {
        return placesRepository.findById(historyEntranceModel.getPlaces().getId())
                .orElseThrow(() -> new ServiceException(Error.INVALID_PLACE_DATA));
    }

    private User getUser(HistoryEntranceModel historyEntranceModel) {
        return userRepository.findUserByEmail(historyEntranceModel.getUser().getEmail())
                .orElseThrow(() -> new ServiceException(Error.INVALID_USER_DATA));
    }

    private Product getProductRepositoryById(HistoryEntranceModel historyEntranceModel) {
        return productRepository.findById(historyEntranceModel.getProduct().getId())
                .orElseThrow(() -> new ServiceException(Error.INVALID_PRODUCT_DATA));
    }
}
