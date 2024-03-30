import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {hasAdminRole} from "./core/guard/AdminGuard";
import {hasUserRole} from "./core/guard/UserGuard";

const routes: Routes = [
  {
    path: '',
    loadChildren: () =>  import('./modules/unlogged/unlogged.module').then((m) => m.UnloggedModule)
  },
  {
    path: 'user',
    loadChildren: () =>  import('./modules/user-logged/user-logged.module').then((m) => m.UserLoggedModule),
    canActivate: [hasUserRole]
  },
  {
    path: 'admin',
    loadChildren: () =>  import('./modules/admin-logged/admin-logged.module').then((m) => m.AdminLoggedModule),
    canActivate: [hasAdminRole]
  },
  {
    path: 'system',
    loadChildren: () =>  import('./modules/system-owner/system-owner.module').then((m) => m.SystemOwnerModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
