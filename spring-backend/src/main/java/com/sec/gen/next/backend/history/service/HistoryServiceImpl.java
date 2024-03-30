package com.sec.gen.next.backend.history.service;

import com.sec.gen.next.backend.api.exception.Error;
import com.sec.gen.next.backend.api.exception.ServiceException;
import com.sec.gen.next.backend.api.external.AuthorizedUser;
import com.sec.gen.next.backend.api.external.HistoryEntranceModel;
import com.sec.gen.next.backend.api.external.KafkaChatServiceModel;
import com.sec.gen.next.backend.api.external.KafkaNotifModel;
import com.sec.gen.next.backend.api.internal.*;
import com.sec.gen.next.backend.common.kafka.KafkaChatServiceProducer;
import com.sec.gen.next.backend.device.repository.DeviceRepository;
import com.sec.gen.next.backend.history.repository.HistoryRepository;
import com.sec.gen.next.backend.places.repository.PlacesRepository;
import com.sec.gen.next.backend.product.repository.ProductRepository;
import com.sec.gen.next.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.List;


@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final HistoryMapper historyMapper;
    private final HistoryRepository historyRepository;
    private final UserRepository userRepository;
    private final PlacesRepository placesRepository;
    private final ProductRepository productRepository;
    private final DeviceRepository deviceRepository;
    private final KafkaChatServiceProducer kafkaChatServiceProducer;
    @Override
    public List<HistoryEntranceModel> getHistoryEntranceForUser() {

        List<HistoryEntrance> historyEntrances = historyRepository.findAll();
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return historyEntrances.stream()
                .filter(historyEntrance -> historyEntrance.getUser().getEmail().equals(email))
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

    @Override
    public void addHistoryEntrance(KafkaNotifModel kafkaNotifModel) {
        Places places = placesRepository.findById(kafkaNotifModel.getPlaceId())
                .orElseThrow(() -> new ServiceException(Error.INVALID_PLACE_DATA));

        Product product = deviceRepository.findById(kafkaNotifModel.getDeviceId())
                .orElseThrow(() -> new ServiceException(Error.INVALID_DEVICE_DATA))
                .getProduct();

        User user = userRepository.findUserByEmail(kafkaNotifModel.getEmail())
                .orElseThrow(() -> new ServiceException(Error.INVALID_USER_DATA));

        HistoryEntrance historyEntrance = new HistoryEntrance()
                .setDate(LocalDateTime.now())
                .setUser(user)
                .setPlaces(places)
                .setProduct(product);

        historyRepository.save(historyEntrance);
        kafkaChatServiceProducer.sendMessage(new KafkaChatServiceModel()
                .setUserEmail(user.getEmail())
                .setName(user.getName())
                .setSurname(user.getSurname())
                .setAdminsEmails(getAdmins(places))
                .setMethod(product.getName()));
    }

    private List<String> getAdmins(Places places) {
        return places.getAuthorizedUsers()
                .stream()
                .filter(authorizedUser -> authorizedUser.getAssignmentRole().equals(AssignmentRole.ADMIN) )
                .map(UserPlaceAssignment::getUser)
                .map(User::getEmail)
                .toList();
    }

    @Override
    public List<HistoryEntranceModel> getHistoryEntrance(Integer placeId) {
        return historyRepository.findAll()
                .stream()
                .filter(historyEntrance -> historyEntrance.getPlaces().getId().equals(placeId))
                .map(historyMapper::map)
                .toList();
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
