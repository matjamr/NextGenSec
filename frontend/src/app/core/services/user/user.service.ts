import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, Subscription, tap} from "rxjs";
import {User} from "../../models/User";
import {Router} from "@angular/router";
import {Token} from "../../models/Token";
import {SocialAuthService} from "@abacritt/angularx-social-login";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private userApiUrl: string = "http://localhost:8000/api/user/servicing"
  private securityApiUrl: string = "http://localhost:8000/api/user/security"

  constructor(
    private http: HttpClient,
    private router: Router,
    private authService: SocialAuthService
  ) {
  }

  verifyUser(): Observable<User> {
    return this.http.post<User>(this.securityApiUrl + "/verify", {}, buildHeader())
  }

  getAll(placeRestriction: boolean): Observable<User[]> {
    return this.http.get<User[]>(this.userApiUrl, buildHeader());
  }


  retrieveToken(email: string, password: string): Observable<any> {
    return this.http.post<any>(
      this.securityApiUrl + "/token",
      {email: email, password: password},
      {headers: {source: "JWT"}, withCredentials: true}
    );
  }

  login(email: string, password: string): Subscription {
    return this.retrieveToken(email, password).subscribe({
      next: token => {
        console.log('Token retrieved:', token);
        this.verifyUser().subscribe(user => {
          console.log('User verified:', user);
          this.router.navigate(["/finishLogin"])
        }, error => console.log(error))
      }
    });
  }

  register(username: string, password: string, source: string): Observable<User> {
    return this.http.post<User>(this.userApiUrl, {
      email: username,
      password: password,
      source: source
    })
  }

  add(username: string): Observable<User> {
    return this.http.post<User>(this.userApiUrl + "/add", {email: username, passwordChange: true}, buildHeader())
  }

  update(user: any) {
    return this.http.post<User>(this.userApiUrl + "/update", user, {withCredentials: true})
  }


  addUser(user: any) {
    return this.http.post<User>(this.userApiUrl, user, {withCredentials: true})
  }

  logout(): Observable<any> {
    return this.http.post(this.securityApiUrl + "/logout", {}, {withCredentials: true})
        .pipe(tap(() => this.authService.signOut()));
  }

  oauth2Register(source: string, token: string): Observable<User> {
    return this.http.post<User>(this.userApiUrl, null, {headers: {source: source, Authorization: 'Bearer ' + token}});
  }

  oauth2Login(source: string, token: string): Observable<Token> {
    return this.http.post<any>(
      this.securityApiUrl + "/token",
      {},
      {headers: {source: source, token: token}, withCredentials: true}
    );
  }

  findAll() {
    return this.http.get<User[]>(this.userApiUrl, {withCredentials: true})
  }

  refreshToken(): Observable<Token> {
    return this.http.post<Token>(this.securityApiUrl + "/refresh", {}, {withCredentials: true})
  }
}

export const buildHeader = () => {
  return {
    headers: {
      "source": String(localStorage.getItem("source"))
    },
    withCredentials: true
  }
}
