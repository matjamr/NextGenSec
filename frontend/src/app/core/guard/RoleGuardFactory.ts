import {CanActivateFn, Router} from "@angular/router";
import {inject} from "@angular/core";
import {map} from "rxjs";
import {User} from "../models/User";
import {UserService} from "../services/user/user.service";

export function roleGuardFactory(role: string): CanActivateFn {
  return (route, state) => {
    const router: Router = inject(Router);

    return inject(UserService).verifyUser().pipe(map((user: User) => {
      if(user.role !== role) {
        router.navigate(['/unauthorized']);
        return false;
      }

      return true;
    }));
  };
}

export const userAuthGuard: CanActivateFn = roleGuardFactory('user');
export const adminAuthGuard: CanActivateFn = roleGuardFactory('admin');
export const systemAuthGuard: CanActivateFn = roleGuardFactory('system');
