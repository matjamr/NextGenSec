import {inject, Injectable} from "@angular/core";
import {ActivatedRoute, CanActivateFn, Router} from "@angular/router";
import {UserService} from "../services/user/user.service";

export const hasUserRole: CanActivateFn = (route, state) => {
  const router: Router = inject(Router)

  console.log(route.url[1])
  // let user = inject(UserService).verifyUserToGivenPlace({placeName: String(route.url[1]), role: "USER"});

  // user.subscribe(
  //   data => console.log(data),
  //   error => router.navigate(['unauthorized'])
  // )

  return true;
}
