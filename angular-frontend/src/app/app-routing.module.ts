import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {hasUserRole} from "./core/guard/UserGuard";

const routes: Routes = [
  {
    path: '',
    loadChildren: () =>  import('./modules/unlogged/unlogged.module').then((m) => m.UnloggedModule)
  },
  {
    path: 'USER/:placeName',
    loadChildren: () =>  import('./modules/user-logged/user-logged.module').then((m) => m.UserLoggedModule),
    canActivate: [hasUserRole]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
