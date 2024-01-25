package com.sec.gen.next.backend.method.service;

import com.sec.gen.next.backend.api.exception.Error;
import com.sec.gen.next.backend.api.exception.ServiceException;
import com.sec.gen.next.backend.api.external.MethodModel;
import com.sec.gen.next.backend.api.internal.*;
import com.sec.gen.next.backend.image.repository.ImageRepository;
import com.sec.gen.next.backend.method.mapper.MethodMapper;
import com.sec.gen.next.backend.product.repository.ProductRepository;
import com.sec.gen.next.backend.user.repository.UserRepository;
import com.sec.gen.next.backend.method.repository.MethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@RequiredArgsConstructor
public class MethodServiceImpl implements MethodService {

    private final MethodMapper methodMapper;
    private final MethodRepository methodRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;

    @Override
    public void add(MethodModel methodModel) {
        Method method = methodMapper.map(methodModel);
        method.setUser(getUser(method))
                .setProduct(getProduct(method))
                .setImages(getImages(method));

        methodRepository.save(method);
    }

    @Override
    public void delete(MethodModel methodModel) {

    }

    @Override
    public List<MethodModel> findAll() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return methodRepository.findAll()
                .stream()
                .filter(method -> method.getUser().getEmail().equals(email))
                .map(methodMapper::map)
                .toList();
    }

    @Override
    public void update(MethodModel methodModel) {

    }

    private User getUser(Method method) {
        return userRepository.findUserByEmail(method.getUser().getEmail())
                .orElseThrow(() -> new ServiceException(Error.INVALID_USER_DATA));
    }

    private Product getProduct(Method method) {
        return productRepository.findById(method.getProduct().getId())
                .orElseThrow(() -> new ServiceException(Error.INVALID_PRODUCT_DATA));
    }

    private List<Image> getImages(Method method) {
        return imageRepository.findAllById(method.getImages().stream().map(Image::getId).toList());
    }
}
