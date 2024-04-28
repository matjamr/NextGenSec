import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {adminAuthGuard, systemAuthGuard, userAuthGuard} from "./core/guard/RoleGuardFactory";

const routes: Routes = [
  {
    path: '',
    loadChildren: () =>  import('./modules/unlogged/unlogged.module').then((m) => m.UnloggedModule)
  },
  {
    path: 'user',
    loadChildren: () =>  import('./modules/user-logged/user-logged.module').then((m) => m.UserLoggedModule),
    canActivate: [userAuthGuard]
  },
  {
    path: 'admin',
    loadChildren: () =>  import('./modules/admin-logged/admin-logged.module').then((m) => m.AdminLoggedModule),
    canActivate: [adminAuthGuard]
  },
  {
    path: 'system',
    loadChildren: () =>  import('./modules/system-owner/system-owner.module').then((m) => m.SystemOwnerModule),
    canActivate: [systemAuthGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
