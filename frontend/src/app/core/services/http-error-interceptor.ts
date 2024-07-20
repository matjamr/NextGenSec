import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, switchMap, tap, throwError} from 'rxjs';
import {catchError, retry} from 'rxjs/operators';
import {NotificationService} from "./notification/notification.service";
import {Router} from "@angular/router";
import {UserService} from "./user/user.service";

@Injectable()
export class HttpErrorInterceptor implements HttpInterceptor {

  constructor(private notificationService: NotificationService,
              private router: Router,
              private authService: UserService) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request)
      .pipe(
        retry(1),
        catchError((error: HttpErrorResponse) => {
          let errorMessage = '';
          if (error.error instanceof ErrorEvent) {
            errorMessage = `Error: ${error.error.message}`;
          } else {
            errorMessage = error.error.message;
          }

          console.log(errorMessage)

          if (errorMessage === 'Expired JWT token' || !getCookie('access_token'))
            return this.authService.refreshToken().pipe(
              tap(() => console.log('Token refreshed')),
              switchMap(() => {
                const clonedRequest = request.clone();
                return next.handle(clonedRequest);
              }),
              catchError((refreshError: HttpErrorResponse) => {
                this.router.navigate(['/login']).then(() => {
                  console.log('Expired JWT Token');
                });

                this.notificationService.error('HTTP Error', errorMessage);
                return throwError(refreshError);
              })
            );
          return throwError(errorMessage);
        })
      )

    function getCookie(name: string): string | null {
      const nameLenPlus = (name.length + 1);
      return document.cookie
        .split(';')
        .map(c => c.trim())
        .filter(cookie => {
          return cookie.substring(0, nameLenPlus) === `${name}=`;
        })
        .map(cookie => {
          return decodeURIComponent(cookie.substring(nameLenPlus));
        })[0] || null;
    }
  }
}
