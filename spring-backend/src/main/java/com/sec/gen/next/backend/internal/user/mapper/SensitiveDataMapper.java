package com.sec.gen.next.backend.internal.user.mapper;

import com.sec.gen.next.backend.internal.api.external.SensitiveDataModel;
import com.sec.gen.next.backend.internal.api.internal.SensitiveData;
import com.sec.gen.next.backend.internal.image.mapper.ImageMapper;
import com.sec.gen.next.backend.internal.product.mapper.ProductMapper;
import org.mapstruct.Mapper;

@Mapper(uses = {ProductMapper.class, ImageMapper.class})
public interface SensitiveDataMapper {
    SensitiveData map(SensitiveDataModel sensitiveDataModel);
    SensitiveDataModel map(SensitiveData sensitiveDataModel);
}
