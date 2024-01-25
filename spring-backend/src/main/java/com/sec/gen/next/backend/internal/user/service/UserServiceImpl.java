package com.sec.gen.next.backend.internal.user.service;

import com.sec.gen.next.backend.internal.api.exception.Error;
import com.sec.gen.next.backend.internal.api.exception.ServiceException;
import com.sec.gen.next.backend.internal.api.external.AdditionalInformationUpdateModel;
import com.sec.gen.next.backend.internal.api.external.SensitiveDataModel;
import com.sec.gen.next.backend.internal.api.external.UserModel;
import com.sec.gen.next.backend.internal.api.external.UserPlaceModel;
import com.sec.gen.next.backend.internal.image.repository.ImageRepository;
import com.sec.gen.next.backend.internal.api.internal.*;
import com.sec.gen.next.backend.internal.places.repository.PlacesRepository;
import com.sec.gen.next.backend.internal.user.mapper.SensitiveDataMapper;
import com.sec.gen.next.backend.internal.user.repository.SensitiveDataRepository;
import com.sec.gen.next.backend.internal.user.repository.UserPlaceAssignmentRepository;
import com.sec.gen.next.backend.internal.user.repository.UserRepository;
import com.sec.gen.next.backend.internal.product.repository.ProductRepository;
import com.sec.gen.next.backend.internal.security.builder.Builder;
import com.sec.gen.next.backend.internal.user.mapper.UserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

import static com.sec.gen.next.backend.internal.api.exception.Error.INVALID_USER_DATA;
import static com.sec.gen.next.backend.internal.api.exception.Error.UNAUTHORIZED;


@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserPlaceAssignmentRepository userPlaceAssignmentRepository;
    private final PlacesRepository placesRepository;
    private final Builder<User, User> usertoDbBuilder;
    private final Builder<ClaimsUser, User> claimsToUserBuilder;
    private final UserMapper userMapper;
    private final SensitiveDataRepository sensitiveDataRepository;
    private final SensitiveDataMapper sensitiveDataMapper;
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;

    @Override
    public User save(UserModel userModel) {
        return userRepository.save(userMapper.from(userModel));
    }

    @Override
    public User update(AdditionalInformationUpdateModel additionalInformationUpdateModel) {
        return null;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ServiceException(Error.INVALID_USER_DATA));
    }

    @Override
    public User loginUser(ClaimsUser claimsUser, RegisterSource s) {
        userRepository.findUserByEmail(claimsUser.getEmail())
                .ifPresentOrElse(user -> {
                    userRepository.save(usertoDbBuilder.apply(user));
                }, () -> registerUser(claimsUser));

        return null;
    }

    private void registerUser(ClaimsUser claimsUser) {
        userRepository.save(usertoDbBuilder.apply(
                claimsToUserBuilder.apply(claimsUser)));
    }

    @Override
    public User verify(ClaimsUser claimsUser) {
        return userRepository.findUserByEmail(claimsUser.getEmail())
                .orElseThrow(() -> new ServiceException(Error.INVALID_USER_DATA));
    }

    @Override
    public List<UserModel> findAll() {
        return userRepository.findAll().stream().map(userMapper::from).toList();
    }

    @Override
    public User verifyPlace(ClaimsUser attribute, UserPlaceModel userPlaceModel) {
        Places foundPlace = placesRepository.findByPlaceName(userPlaceModel.getPlaceName())
                .orElseThrow(() -> new ServiceException(Error.INVALID_PLACE_DATA));

        return foundPlace.getAuthorizedUsers().stream()
                .filter(userPlaceAssignment -> userPlaceAssignment.getAssignmentRole().equals(userPlaceModel.getRole()))
                .map(UserPlaceAssignment::getUser)
                .filter(user -> user.getEmail().equals(attribute.getEmail()))
                .findFirst()
                .orElseThrow(() -> new ServiceException(UNAUTHORIZED));
    }

    @Transactional
    @Override
    public List<SensitiveDataModel> addSensitiveData(ClaimsUser claimsUser, SensitiveDataModel sensitiveDataModel) {
        Image image = imageRepository.findById(sensitiveDataModel.getImage().getId()).get();
        Product product = productRepository.findById(sensitiveDataModel.getProduct().getId()).get();

        SensitiveData beforeSaveData = sensitiveDataMapper.map(sensitiveDataModel)
                .setId(UUID.randomUUID().toString())
                .setImage(image)
                .setProduct(product);

        SensitiveData sensitiveData = sensitiveDataRepository.save(beforeSaveData);
        User user = findUserByEmail(claimsUser.getEmail());
        user.sensitiveData().add(sensitiveData);
        userRepository.save(user);

        return List.of(sensitiveDataMapper.map(sensitiveData));
    }

    @Transactional
    @Override
    public List<SensitiveDataModel> getSensitiveData(String email) {
        return userRepository.findUserByEmail(email)
                .map(User::sensitiveData)
                .orElseThrow(() -> new ServiceException(INVALID_USER_DATA))
                .stream()
                .map(sensitiveDataMapper::map)
                .toList();
    }
}
