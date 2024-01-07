package com.sec.gen.next.backend.method.mapper;

import com.sec.gen.next.backend.api.external.MethodModel;
import com.sec.gen.next.backend.api.internal.Method;
import com.sec.gen.next.backend.product.mapper.ProductMapper;
import com.sec.gen.next.backend.user.mapper.UserMapper;
import org.mapstruct.Mapper;

@Mapper(uses = {UserMapper.class, ProductMapper.class})
public interface MethodMapper {
    Method map(MethodModel methodModel);
    MethodModel map(Method method);
}
