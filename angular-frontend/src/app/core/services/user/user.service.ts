import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, Subscription, tap} from "rxjs";
import {User} from "../../models/User";
import {Router} from "@angular/router";

@Injectable({
    providedIn: 'root'
})
export class UserService {

    private userApiUrl: string = "http://localhost:8080/api/user"
    private securityApiUrl: string = "http://localhost:8081/api/security/"

    constructor(
        private http: HttpClient,
        private router: Router
    ) {
    }

    verifyUser(): Observable<User> {
        return this.http.post<User>(this.userApiUrl + "/verify", {}, buildHeader())
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


    retrieveToken(email: string, password: string): Observable<ArrayBuffer> {
        return this.http.post<string>(
            this.securityApiUrl + "token",
            {email: email, password: password},
            // @ts-ignore
            {responseType: "text"}
        ).pipe(
            tap(token => {
                localStorage.setItem("token", String(token));
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
            }, error: error => alert("Wrong credentials")
        });
    }

    register(username: string, password: string, source: string): Observable<User> {
        return this.http.post<User>(this.userApiUrl + "/register", {
            email: username,
            password: password
        }, {headers: {source: source}})
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
