import {inject} from "@angular/core";
import {CanActivateFn, Router} from "@angular/router";
import {UserService} from "../services/user/user.service";
import {PlaceService} from "../services/place/place.service";

export const hasAdminRole: CanActivateFn = (route, state) => {
  const router: Router = inject(Router)

  let user = inject(UserService).verifyUser();
  let places = inject(PlaceService).getPlacesByUser();

  places.subscribe(place => {
    user.subscribe(
      data => {
        // console.log(place[0]?.authorizedUsers[0]?.assignmentRole.toLowerCase())
        if(place[0]?.authorizedUsers[0]?.assignmentRole.toLowerCase() === "admin") {
        } else {
          router.navigate(["/unauthorized"])
        }
      }
    )
  })

  return true;
}
