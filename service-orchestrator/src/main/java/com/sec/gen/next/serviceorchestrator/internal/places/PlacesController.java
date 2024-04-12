package com.sec.gen.next.serviceorchestrator.internal.places;

import com.next.gen.sec.model.PlacesModel;
import com.sec.gen.next.serviceorchestrator.common.templates.CrudService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/places")
public class PlacesController {

    @Qualifier("crudPlaceService")
    private final CrudService<PlacesModel, PlacesModel, String> crudPlaceService;

    public PlacesController(CrudService<PlacesModel, PlacesModel, String> crudPlaceService) {
        this.crudPlaceService = crudPlaceService;
    }

//    private final UpdateService<PlacesModel, PlacesModel> addUserPlaceAssignmentService;
//    private final UpdateService<PlacesModel, PlacesModel> deleteUserPlaceAssignmentService;
//    private final UpdateService<PlacesModel, PlacesModel> updateUserPlaceAssignmentService;

    @PostMapping
    public PlacesModel addPlace(@RequestBody PlacesModel placesModel) {
        return crudPlaceService.save(placesModel);
    }

    @GetMapping
    public List<PlacesModel> getPlaces() {
        return crudPlaceService.findAll();
    }

//    @DeleteMapping
//    public PlacesModel deletePlace(@RequestBody PlacesModel placesModel) {
//        return placesService.delete(placesModel);
//    }
}
