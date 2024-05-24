package com.sec.gen.next.serviceorchestrator.security.mapper;

import com.next.gen.sec.model.GoogleAuthorizedUser;
import com.next.gen.sec.model.PlacesModel;
import com.next.gen.sec.model.UserModel;
import com.sec.gen.next.serviceorchestrator.api.CustomAuthentication;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.function.Supplier;

@Mapper
public abstract class CustomAuthenticationMapper {

    protected Supplier<List<PlacesModel>> placesForUserSupplier;

    public abstract CustomAuthentication map(UserModel user);

    @AfterMapping
    protected void afterMapping(@MappingTarget CustomAuthentication customAuthentication, UserModel user) {
    }


    @Autowired
    public void setPlacesForUserSupplier(Supplier<List<PlacesModel>> placesForUserSupplier) {
        this.placesForUserSupplier = placesForUserSupplier;
    }
}
