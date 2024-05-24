package com.sec.gen.next.serviceorchestrator.internal.product.service;

import com.next.gen.api.Product;
import com.next.gen.api.SensitiveData;
import com.next.gen.api.User;
import com.next.gen.sec.model.*;
import com.sec.gen.next.serviceorchestrator.api.CustomAuthentication;
import com.sec.gen.next.serviceorchestrator.common.templates.CrudService;
import com.sec.gen.next.serviceorchestrator.common.templates.QueryService;
import com.sec.gen.next.serviceorchestrator.exception.Error;
import com.sec.gen.next.serviceorchestrator.exception.ServiceException;
import com.sec.gen.next.serviceorchestrator.internal.email.repository.UserRepository;
import com.sec.gen.next.serviceorchestrator.internal.image.repository.ImageRepository;
import com.sec.gen.next.serviceorchestrator.internal.product.mapper.ProductMapper;
import com.sec.gen.next.serviceorchestrator.internal.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

import static com.sec.gen.next.serviceorchestrator.exception.Error.INVALID_PRODUCT_DATA;
import static com.sec.gen.next.serviceorchestrator.exception.Error.INVALID_USER_DATA;

@Service
@RequiredArgsConstructor
public class UserProductQueryingService implements CrudService<SensitiveDataModel, SensitiveDataModel, String> {

    private final UserRepository userRepository;
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;

    @Override
    public List<SensitiveDataModel> findAll() {
        CustomAuthentication user = (CustomAuthentication) SecurityContextHolder.getContext().getAuthentication();

        if(!user.getRole().equals(Role.USER)) {
            throw new ServiceException(Error.UNAUTHORIZED);
        }

        return userRepository.findAll()
                .stream()
                .map(User::getSensitiveData)
                .flatMap(Collection::stream)
                .filter(sensitiveData -> sensitiveData.getUser().getEmail().equals(user.getEmail()))
                .map(productMapper::map)
                .toList();
    }

    @Override
    public SensitiveDataModel save(SensitiveDataModel sensitiveDataModel) {
        CustomAuthentication user = (CustomAuthentication) SecurityContextHolder.getContext().getAuthentication();

        User userFromDb = userRepository.findByEmail(user.getEmail())
                .orElseThrow(INVALID_USER_DATA::getError);

        if(userFromDb.getSensitiveData()
                .stream()
                .anyMatch(sensitiveData -> sensitiveData.getProduct().getId().equals(sensitiveDataModel.getProduct().getId()))) {
            throw new ServiceException(INVALID_PRODUCT_DATA);
        }

        var aa = productMapper.map(sensitiveDataModel);
        var images = imageRepository.findAllById(sensitiveDataModel.getImages().stream().map(ImageModel::getId).toList());


        aa.setUser(userFromDb);
        aa.setImages(images);
        userFromDb.getSensitiveData().add(aa);

        userRepository.save(userFromDb);

        return productMapper.map(aa);
    }

    @Override
    public SensitiveDataModel delete(SensitiveDataModel sensitiveDataModel) {
        sensitiveDataModel.getProduct().getId();

        CustomAuthentication user = (CustomAuthentication) SecurityContextHolder.getContext().getAuthentication();
        User userFromDb = userRepository.findByEmail(user.getEmail())
                .orElseThrow(INVALID_USER_DATA::getError);

        if(userFromDb.getSensitiveData()
                .stream()
                .noneMatch(sensitiveData -> sensitiveData.getProduct().getId().equals(sensitiveDataModel.getProduct().getId()))) {
            throw new ServiceException(INVALID_PRODUCT_DATA);
        }

        userFromDb.getSensitiveData().removeIf(sensitiveData -> sensitiveData.getProduct().getId().equals(sensitiveDataModel.getProduct().getId()));
        userRepository.save(userFromDb);
        return sensitiveDataModel;
    }
}
