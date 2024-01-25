package com.sec.gen.next.backend.user.mapper;

import com.sec.gen.next.backend.api.external.SensitiveDataModel;
import com.sec.gen.next.backend.api.internal.SensitiveData;
import com.sec.gen.next.backend.image.mapper.ImageMapper;
import com.sec.gen.next.backend.product.mapper.ProductMapper;
import org.mapstruct.Mapper;

@Mapper(uses = {ProductMapper.class, ImageMapper.class})
public interface SensitiveDataMapper {
    SensitiveData map(SensitiveDataModel sensitiveDataModel);
    SensitiveDataModel map(SensitiveData sensitiveDataModel);
}
