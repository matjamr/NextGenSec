package com.sec.gen.next.backend.user.service;

import com.sec.gen.next.backend.api.exception.Error;
import com.sec.gen.next.backend.api.exception.ServiceException;
import com.sec.gen.next.backend.api.external.*;
import com.sec.gen.next.backend.api.internal.*;
import com.sec.gen.next.backend.common.address.AddressRepository;
import com.sec.gen.next.backend.common.kafka.KafkaOutboundEmailProducer;
import com.sec.gen.next.backend.image.repository.ImageRepository;
import com.sec.gen.next.backend.places.repository.PlacesRepository;
import com.sec.gen.next.backend.product.repository.ProductRepository;
import com.sec.gen.next.backend.security.builder.Builder;
import com.sec.gen.next.backend.user.mapper.SensitiveDataMapper;
import com.sec.gen.next.backend.user.mapper.UserMapper;
import com.sec.gen.next.backend.user.repository.SensitiveDataRepository;
import com.sec.gen.next.backend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.sec.gen.next.backend.api.exception.Error.INVALID_USER_DATA;
import static com.sec.gen.next.backend.api.exception.Error.UNAUTHORIZED;
import static java.util.Objects.nonNull;


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
    private final List<Executor<User, UserModel>> updateUserConsumerList;
    private final AddressRepository addressRepository;
    private final KafkaOutboundEmailProducer kafkaOutboundEmailProducer;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(8);

    @Override
    public User save(UserModel userModel, RegisterSource source) {
        kafkaOutboundEmailProducer.sendMessage(new OutboundEmailModel()
                .setEmail(userModel.getEmail())
                .setParams(Map.of("email", userModel.getEmail()))
                .setStrategy("ACCOUNT_CREATE"));

        return userRepository.save(
                userMapper.from(userModel)
                        .setCreationDate(LocalDateTime.now())
                        .setRegistrationSource(source)
                        .setPasswordChange(Optional.ofNullable(userModel.getPasswordChange()).orElse("false"))
                        .setPassword(passwordEncoder.encode(
                                Optional.ofNullable(userModel.getPassword())
                                        .orElseGet(() -> {
                                            var a = UUID.randomUUID().toString();
                                            kafkaOutboundEmailProducer.sendMessage(new OutboundEmailModel()
                                                    .setEmail(userModel.getEmail())
                                                    .setParams(Map.of("email", userModel.getEmail()))
                                                    .setParams(Map.of("password", a))
                                                    .setStrategy("PASSWD_CHANGE"));
                                            return a;
                                        })
                        ))
        );
    }

    @Override
    public UserModel update(UserModel userModel) {
        User user = userRepository.findUserByEmail(userModel.getEmail())
                    .orElseThrow(() -> new ServiceException(Error.INVALID_USER_DATA));

        updateUserConsumerList
                .stream()
                .filter(consumer -> consumer.shouldAccept(user, userModel))
                .forEach(consumer -> consumer.accept(user, userModel));

        return userMapper.from(userRepository.save(user));
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
    @Transactional
    public UserModel verify() {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ServiceException(Error.INVALID_USER_DATA));

        if(nonNull(user.getAddress())) {
            user.setAddress(addressRepository.findById(user.getAddress().getId())
                    .orElseThrow(() -> new ServiceException(Error.INVALID_ADDRESS_DATA)));
        }

        UserModel userModel = userMapper.from(user);

        if(nonNull(user.getSensitiveData())) {
            userModel.setSupportedProducts(user.getSensitiveData().stream()
                    .map(sensitiveData -> ProductModel.builder()
                            .id(sensitiveData.getProduct().getId())
                            .name(sensitiveData.getProduct().getName()).build())
                            .distinct()
                    .toList());
        }

        return userModel;
    }

    @Override
    public List<UserModel> findAll(boolean placeRestriction) {
        List<UserModel> userModels = userRepository.findAll().stream()
                .map(userMapper::from).toList();

        if(!placeRestriction) {
            return userModels;
        }

        String adminEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Places place = placesRepository.findAll().stream()
                .filter(places -> places.getAuthorizedUsers().stream()
                        .anyMatch(userPlaceAssignment -> userPlaceAssignment.getUser().getEmail().equals(adminEmail)))
                .findFirst()
                .orElseThrow(() -> new ServiceException(Error.UNAUTHORIZED));

        return place.getAuthorizedUsers().stream()
                .map(UserPlaceAssignment::getUser)
                .map(userMapper::from)
                .toList();
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
        user.getSensitiveData().add(sensitiveData);
        userRepository.save(user);

        return List.of(sensitiveDataMapper.map(sensitiveData));
    }

    @Transactional
    @Override
    public List<SensitiveDataModel> getSensitiveData(String email) {
        return userRepository.findUserByEmail(email)
                .map(User::getSensitiveData)
                .orElseThrow(() -> new ServiceException(INVALID_USER_DATA))
                .stream()
                .map(sensitiveDataMapper::map)
                .toList();
    }

    @Override
    public UserModel oauth2Login(RegisterSource source) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        if(userRepository.findUserByEmail(email).isPresent()) {
            throw new ServiceException(INVALID_USER_DATA);
        }

        User user = userRepository.save(new User()
                .setEmail(email)
                .setCreationDate(LocalDateTime.now())
                .setRegistrationSource(source)
                .setPasswordChange("false"));

        return userMapper.from(user);
    }
}
