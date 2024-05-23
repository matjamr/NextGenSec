package com.sec.gen.next.serviceorchestrator.internal.places.mapper;

import com.next.gen.api.Places;
import com.next.gen.sec.model.PlacesModel;
import com.sec.gen.next.serviceorchestrator.common.templates.ViewershipBuilder;
import com.sec.gen.next.serviceorchestrator.internal.device.mapper.DeviceMapper;
import com.sec.gen.next.serviceorchestrator.internal.product.mapper.ProductMapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.function.Function;

@Mapper(uses = {UserPlaceAssignmentMapper.class, UserMapper.class, DeviceMapper.class, ProductMapper.class})
public abstract class PlacesMapper {

    protected Function<List<PlacesModel>, List<PlacesModel>> viewershipBuilder;

    public abstract PlacesModel map(Places placeEntity);
    public abstract Places map(PlacesModel placesModel);

    public List<PlacesModel> map(List<Places> placesModels) {
        return this.viewershipBuilder.apply(placesModels.stream()
                .map(this::map)
                .toList());
    }

    @Autowired
    public void setViewershipBuilder(Function<List<PlacesModel>, List<PlacesModel>> viewershipBuilder) {
        this.viewershipBuilder = viewershipBuilder;
    }
}
