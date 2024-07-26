package com.sec.gen.next.serviceorchestrator.internal.places;

import com.next.gen.sec.model.ModifyProductsPlaceModel;
import com.next.gen.sec.model.ModifyUserToPlaceModel;
import com.next.gen.sec.model.PlacesModel;
import com.sec.gen.next.serviceorchestrator.common.templates.CrudService;
import com.sec.gen.next.serviceorchestrator.common.templates.DeleteService;
import com.sec.gen.next.serviceorchestrator.common.templates.UpdateService;
import jakarta.transaction.Transactional;
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
    private final Supplier<List<PlacesModel>> placesForUserSupplier;
    private final DeleteService<List<String>, List<String>> placesDeleteService;
    @Qualifier("changeUserToPlaceService")
    private final UpdateService<ModifyUserToPlaceModel, PlacesModel> changeUserToPlaceService;
    @Qualifier("addUserToPlaceService")
    private final UpdateService<ModifyUserToPlaceModel, PlacesModel> addUserToPlaceService;
    @Qualifier("removeUserFromPlaceService")
    private final UpdateService<ModifyUserToPlaceModel, PlacesModel> removeUserFromPlaceService;
    @Qualifier("addProductToPlaceService")
    private final UpdateService<ModifyProductsPlaceModel, PlacesModel> addProductToPlaceService;


    public PlacesController(CrudService<PlacesModel, PlacesModel, String> crudPlaceService,
                            Supplier<List<PlacesModel>> placesForUserSupplier,
                            DeleteService<List<String>, List<String>> placesDeleteService,
                            UpdateService<ModifyUserToPlaceModel, PlacesModel> changeUserToPlaceService,
                            UpdateService<ModifyUserToPlaceModel, PlacesModel> addUserToPlaceService,
                            UpdateService<ModifyUserToPlaceModel, PlacesModel> removeUserFromPlaceService,
                            UpdateService<ModifyProductsPlaceModel, PlacesModel> addProductToPlaceService) {
        this.crudPlaceService = crudPlaceService;
        this.placesForUserSupplier = placesForUserSupplier;
        this.placesDeleteService = placesDeleteService;
        this.changeUserToPlaceService = changeUserToPlaceService;
        this.addUserToPlaceService = addUserToPlaceService;
        this.removeUserFromPlaceService = removeUserFromPlaceService;
        this.addProductToPlaceService = addProductToPlaceService;
    }

    @Transactional
    @PostMapping
    public PlacesModel addPlace(@RequestBody PlacesModel placesModel) {
        return crudPlaceService.save(placesModel);
    }

    @Transactional
    @GetMapping
    public List<PlacesModel> getPlaces() {
        return crudPlaceService.findAll();
    }

    @Transactional
    @GetMapping("/{placeName}")
    public PlacesModel getPlaceByName(@PathVariable String placeName) {
        return crudPlaceService.findBy(placeName);
    }


    @Transactional
    @GetMapping("/user")
    public List<PlacesModel> getPlace() {
        return placesForUserSupplier.get();
    }

    @Transactional
    @PostMapping("/product")
    public PlacesModel addProductToPlace(@RequestBody ModifyProductsPlaceModel modifyUserToPlaceModel) {
        return addProductToPlaceService.update(modifyUserToPlaceModel);
    }

    @Transactional
    @DeleteMapping
    public List<String> deletePlace(@RequestBody List<String> placesIds) {
        return placesDeleteService.delete(placesIds);
    }

    @PutMapping
    @Transactional
    public PlacesModel updatePlace(@RequestBody PlacesModel placesModel) {
        return crudPlaceService.update(placesModel);
    }

    @Transactional
    @PutMapping("/admin")
    public PlacesModel changeUserToPlace(@RequestBody ModifyUserToPlaceModel modifyUserToPlaceModel) {
        return changeUserToPlaceService.update(modifyUserToPlaceModel);
    }

    @Transactional
    @PostMapping("/admin")
    public PlacesModel addUserToPlace(@RequestBody ModifyUserToPlaceModel modifyUserToPlaceModel) {
        return addUserToPlaceService.update(modifyUserToPlaceModel);
    }

    @Transactional
    @DeleteMapping("/admin")
    public PlacesModel removeUserFromPlace(@RequestBody ModifyUserToPlaceModel modifyUserToPlaceModel) {
        return removeUserFromPlaceService.update(modifyUserToPlaceModel);
    }
}
