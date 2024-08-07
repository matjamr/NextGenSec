package com.sec.gen.next.serviceorchestrator.internal.places.mapper.viewership;

import com.next.gen.sec.model.PlacesModel;
import com.sec.gen.next.serviceorchestrator.common.templates.ViewershipBuilder;

import java.util.List;

public class PlacesUserViewershipBuilder implements ViewershipBuilder<List<PlacesModel>> {

    @Override
    public List<PlacesModel> apply(List<PlacesModel> obj) {
        return obj;
    }
}
