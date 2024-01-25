package com.sec.gen.next.backend.internal.method.mapper;

import com.sec.gen.next.backend.internal.api.external.MethodModel;
import com.sec.gen.next.backend.internal.api.internal.Method;
import com.sec.gen.next.backend.internal.user.mapper.UserMapper;
import com.sec.gen.next.backend.internal.product.mapper.ProductMapper;
import org.mapstruct.Mapper;

@Mapper(uses = {UserMapper.class, ProductMapper.class})
public interface MethodMapper {
    Method map(MethodModel methodModel);
    MethodModel map(Method method);
}
