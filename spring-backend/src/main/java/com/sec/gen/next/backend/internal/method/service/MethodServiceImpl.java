package com.sec.gen.next.backend.internal.method.service;

import com.sec.gen.next.backend.internal.api.exception.Error;
import com.sec.gen.next.backend.internal.api.exception.ServiceException;
import com.sec.gen.next.backend.internal.api.external.MethodModel;
import com.sec.gen.next.backend.internal.image.repository.ImageRepository;
import com.sec.gen.next.backend.internal.api.internal.*;
import com.sec.gen.next.backend.internal.method.mapper.MethodMapper;
import com.sec.gen.next.backend.internal.product.repository.ProductRepository;
import com.sec.gen.next.backend.internal.user.repository.UserRepository;
import com.sec.gen.next.backend.internal.method.repository.MethodRepository;
import lombok.RequiredArgsConstructor;

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
    public List<MethodModel> findAll(ClaimsUser principal) {
        return methodRepository.findAll()
                .stream()
                .filter(method -> method.getUser().getEmail().equals(principal.getEmail()))
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
