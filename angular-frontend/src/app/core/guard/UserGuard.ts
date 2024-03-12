import {inject, Injectable} from "@angular/core";
import {ActivatedRoute, CanActivateFn, Router} from "@angular/router";
import {UserService} from "../services/user/user.service";
import {select, Store} from "@ngrx/store";
import {AppState} from "../../app.state";
import {GetPlaces} from "../state/place/place.actions";
import {PlaceService} from "../services/place/place.service";

export const hasUserRole: CanActivateFn = (route, state) => {
  const router: Router = inject(Router)


  let user = inject(UserService).verifyUser();
  let places = inject(PlaceService).getPlacesByUser();

  places.subscribe(place => {
    user.subscribe(
      data => {
        // console.log(place[0]?.authorizedUsers[0]?.assignmentRole.toLowerCase())
        if(place[0]?.authorizedUsers[0]?.assignmentRole.toLowerCase() !== "admin") {
          // console.log("Authorized")
        } else {
          // console.log("Unauthorized")
          router.navigate(["/unauthorized"])
        }
      }
    )
  })

  return true;

}
