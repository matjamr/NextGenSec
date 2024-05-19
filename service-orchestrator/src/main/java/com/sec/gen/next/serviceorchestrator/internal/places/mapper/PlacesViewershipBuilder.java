package com.sec.gen.next.serviceorchestrator.internal.places.mapper;

import com.next.gen.sec.model.PlacesModel;
import com.next.gen.sec.model.Role;
import com.sec.gen.next.serviceorchestrator.api.CustomAuthentication;
import com.sec.gen.next.serviceorchestrator.common.templates.ViewershipBuilder;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;
import java.util.function.Function;

@RequiredArgsConstructor
public class PlacesViewershipBuilder<T> implements Function<T, T> {
    private final Map<Role, ViewershipBuilder<T>> viewershipBuilders;

    @Override
    public T apply(T object) {
        CustomAuthentication user = (CustomAuthentication) SecurityContextHolder.getContext().getAuthentication();

        if(user.getRole() == null || !viewershipBuilders.containsKey(user.getRole()))
            return object;

        return viewershipBuilders.get(user.getRole()).apply(object);
    }
}
