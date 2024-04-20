package com.sec.gen.next.serviceorchestrator.internal.places;

import com.next.gen.sec.model.PlacesModel;
import com.sec.gen.next.serviceorchestrator.common.templates.CrudService;
import com.sec.gen.next.serviceorchestrator.common.templates.DeleteService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Supplier;

@RestController
@RequestMapping("/api/places")
public class PlacesController {

    @Qualifier("crudPlaceService")
    private final CrudService<PlacesModel, PlacesModel, String> crudPlaceService;

    @Qualifier("placesForUserSupplier")
    private final Supplier<PlacesModel> placesForUserSupplier;

    private final DeleteService<List<String>, List<String>> placesDeleteService;


    public PlacesController(CrudService<PlacesModel, PlacesModel, String> crudPlaceService,
                            Supplier<PlacesModel> placesForUserSupplier, DeleteService<List<String>, List<String>> placesDeleteService) {
        this.crudPlaceService = crudPlaceService;
        this.placesForUserSupplier = placesForUserSupplier;
        this.placesDeleteService = placesDeleteService;
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

    @GetMapping("/user")
    public PlacesModel getPlace() {
        return placesForUserSupplier.get();
    }

    @DeleteMapping
    public List<String> deletePlace(@RequestBody List<String> placesIds) {
        return placesDeleteService.delete(placesIds);
    }
}
