package com.sec.gen.next.serviceorchestrator.internal.places;

import com.next.gen.sec.model.ModifyUserToPlaceModel;
import com.next.gen.sec.model.PlacesModel;
import com.sec.gen.next.serviceorchestrator.common.templates.CrudService;
import com.sec.gen.next.serviceorchestrator.common.templates.DeleteService;
import com.sec.gen.next.serviceorchestrator.common.templates.UpdateService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Supplier;

@RequestMapping("/api/places")
@RestController
public class PlacesController {

    @Qualifier("crudPlaceService")
    private final CrudService<PlacesModel, PlacesModel, String> crudPlaceService;
    @Qualifier("placesForUserSupplier")
    private final Supplier<PlacesModel> placesForUserSupplier;
    private final DeleteService<List<String>, List<String>> placesDeleteService;
    @Qualifier("changeUserToPlaceService")
    private final UpdateService<ModifyUserToPlaceModel, PlacesModel> changeUserToPlaceService;
    @Qualifier("addUserToPlaceService")
    private final UpdateService<ModifyUserToPlaceModel, PlacesModel> addUserToPlaceService;
    @Qualifier("removeUserFromPlaceService")
    private final UpdateService<ModifyUserToPlaceModel, PlacesModel> removeUserFromPlaceService;


    public PlacesController(CrudService<PlacesModel, PlacesModel, String> crudPlaceService,
                            Supplier<PlacesModel> placesForUserSupplier, DeleteService<List<String>, List<String>> placesDeleteService, UpdateService<ModifyUserToPlaceModel, PlacesModel> changeUserToPlaceService, UpdateService<ModifyUserToPlaceModel, PlacesModel> addUserToPlaceService, UpdateService<ModifyUserToPlaceModel, PlacesModel> removeUserFromPlaceService) {
        this.crudPlaceService = crudPlaceService;
        this.placesForUserSupplier = placesForUserSupplier;
        this.placesDeleteService = placesDeleteService;
        this.changeUserToPlaceService = changeUserToPlaceService;
        this.addUserToPlaceService = addUserToPlaceService;
        this.removeUserFromPlaceService = removeUserFromPlaceService;
    }

    @PostMapping
    public PlacesModel addPlace(@RequestBody PlacesModel placesModel) {
        return crudPlaceService.save(placesModel);
    }

    @GetMapping
    public List<PlacesModel> getPlaces() {
        return crudPlaceService.findAll();
    }

    @GetMapping("/{placeName}")
    public PlacesModel getPlaceByName(@PathVariable String placeName) {
        return crudPlaceService.findBy(placeName);
    }

    @GetMapping("/user")
    public PlacesModel getPlace() {
        return placesForUserSupplier.get();
    }

    @DeleteMapping
    public List<String> deletePlace(@RequestBody List<String> placesIds) {
        return placesDeleteService.delete(placesIds);
    }

    @PutMapping
    public PlacesModel updatePlace(@RequestBody PlacesModel placesModel) {
        return crudPlaceService.update(placesModel);
    }

    @PutMapping("/admin")
    public PlacesModel changeUserToPlace(@RequestBody ModifyUserToPlaceModel modifyUserToPlaceModel) {
        return changeUserToPlaceService.update(modifyUserToPlaceModel);
    }

    @PostMapping("/admin")
    public PlacesModel addUserToPlace(@RequestBody ModifyUserToPlaceModel modifyUserToPlaceModel) {
        return addUserToPlaceService.update(modifyUserToPlaceModel);
    }

    @DeleteMapping("/admin")
    public PlacesModel removeUserFromPlace(@RequestBody ModifyUserToPlaceModel modifyUserToPlaceModel) {
        return removeUserFromPlaceService.update(modifyUserToPlaceModel);
    }
}
