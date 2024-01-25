package com.sec.gen.next.backend.user.service;

import com.sec.gen.next.backend.api.exception.Error;
import com.sec.gen.next.backend.api.exception.ServiceException;
import com.sec.gen.next.backend.api.external.*;
import com.sec.gen.next.backend.api.internal.*;
import com.sec.gen.next.backend.image.repository.ImageRepository;
import com.sec.gen.next.backend.places.repository.PlacesRepository;
import com.sec.gen.next.backend.user.mapper.SensitiveDataMapper;
import com.sec.gen.next.backend.user.repository.SensitiveDataRepository;
import com.sec.gen.next.backend.user.repository.UserPlaceAssignmentRepository;
import com.sec.gen.next.backend.user.repository.UserRepository;
import com.sec.gen.next.backend.product.repository.ProductRepository;
import com.sec.gen.next.backend.security.builder.Builder;
import com.sec.gen.next.backend.user.mapper.UserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.UUID;

import static com.sec.gen.next.backend.api.exception.Error.INVALID_USER_DATA;
import static com.sec.gen.next.backend.api.exception.Error.UNAUTHORIZED;


@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PlacesRepository placesRepository;
    private final Builder<User, User> usertoDbBuilder;
    private final Builder<AuthorizedUser, User> claimsToUserBuilder;
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
    public User loginUser(RegisterSource s) {
        AuthorizedUser authorizedUser = (AuthorizedUser) SecurityContextHolder.getContext().getAuthentication();
        userRepository.findUserByEmail(authorizedUser.getEmail())
                .ifPresentOrElse(user -> userRepository.save(usertoDbBuilder.apply(user)),
                        () -> registerUser(authorizedUser));

        return null;
    }

    private void registerUser(AuthorizedUser authorizedUser) {
        userRepository.save(usertoDbBuilder.apply(
                claimsToUserBuilder.apply(authorizedUser)));
    }

    @Override
    public User verify() {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ServiceException(Error.INVALID_USER_DATA));
    }

    @Override
    public List<UserModel> findAll() {
        return userRepository.findAll().stream().map(userMapper::from).toList();
    }

    @Override
    public User verifyPlace(UserPlaceModel userPlaceModel) {
        Places foundPlace = placesRepository.findByPlaceName(userPlaceModel.getPlaceName())
                .orElseThrow(() -> new ServiceException(Error.INVALID_PLACE_DATA));

        var email = SecurityContextHolder.getContext().getAuthentication().getName();

        return foundPlace.getAuthorizedUsers().stream()
                .filter(userPlaceAssignment -> userPlaceAssignment.getAssignmentRole().equals(userPlaceModel.getRole()))
                .map(UserPlaceAssignment::getUser)
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new ServiceException(UNAUTHORIZED));
    }

    @Transactional
    @Override
    public List<SensitiveDataModel> addSensitiveData(SensitiveDataModel sensitiveDataModel) {
        Image image = imageRepository.findById(sensitiveDataModel.getImage().getId()).get();
        Product product = productRepository.findById(sensitiveDataModel.getProduct().getId()).get();
        var email = SecurityContextHolder.getContext().getAuthentication().getName();

        SensitiveData beforeSaveData = sensitiveDataMapper.map(sensitiveDataModel)
                .setId(UUID.randomUUID().toString())
                .setImage(image)
                .setProduct(product);

        SensitiveData sensitiveData = sensitiveDataRepository.save(beforeSaveData);
        User user = findUserByEmail(email);
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
