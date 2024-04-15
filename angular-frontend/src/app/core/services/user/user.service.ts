import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, Subscription, tap} from "rxjs";
import {User} from "../../models/User";
import {Router} from "@angular/router";

@Injectable({
    providedIn: 'root'
})
export class UserService {

    private userApiUrl: string = "http://localhost:8081/api/user/servicing"
    private securityApiUrl: string = "http://localhost:8081/api/user/security"

    constructor(
        private http: HttpClient,
        private router: Router
    ) {
    }

    verifyUser(): Observable<User> {
        return this.http.post<User>(this.securityApiUrl + "/verify", {}, buildHeader())
    }

    getAll(placeRestriction: boolean): Observable<User[]> {
        return this.http.get<User[]>(this.userApiUrl, {
            headers: {
                "token": String(localStorage.getItem("token")),
                "source": String(localStorage.getItem("source")),
                "placeRestriction": String(placeRestriction)
            }
        })
    }


    retrieveToken(email: string, password: string): Observable<any> {
        return this.http.post<any>(
            this.securityApiUrl + "/token",
            {email: email, password: password},
            {headers: {source: "JWT"}},
        ).pipe(
            tap(token => {
                localStorage.setItem("token", token.token);
                localStorage.setItem("source", "JWT");
            })
        );
    }

    login(email: string, password: string): Subscription {
        return this.retrieveToken(email, password).subscribe({
            next: token => {
                console.log('Token retrieved:', token);
                this.verifyUser().subscribe(user => {
                    console.log('User verified:', user);
                    this.router.navigate(["/choose"])
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
        return this.http.post<User>(this.userApiUrl + "/update", user, buildHeader())
    }

    logout() {
        this.router.navigate(["/login"])
    }

    oauth2Login() {
        return this.http.post<User>(this.userApiUrl + "/oauth2", {}, buildHeader())
    }
}

export const buildHeader = () => {
    return {
        headers: {
            "token": String(localStorage.getItem("token")),
            "source": String(localStorage.getItem("source"))
        }
    }
}
