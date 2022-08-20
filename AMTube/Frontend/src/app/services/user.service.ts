import { Injectable } from '@angular/core';
import {BehaviorSubject, map, Observable} from "rxjs";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {AUTH_TOKEN, URL} from "../constants";
import {LoginForm} from "../model/loginForm.model";
import {Token} from "../model/token.model";
import {RegistrationForm} from "../model/registrationForm.model";
import {User} from "../model/user.model";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private jwtToken$: BehaviorSubject<string | null> = new BehaviorSubject<string | null>(null);

  constructor(private http: HttpClient) {
  }

  setJwtToken(token:string | null) {
    this.jwtToken$.next(token);
  }

  getJwtToken() {
    return this.jwtToken$.asObservable();
  }

  login(account: LoginForm): Observable<Token> {
    // @ts-ignore
    return this.http.post<Token>(URL.LOGIN, account, {observe: 'response'}).pipe(
        map((resp: HttpResponse<Token>) => {
          // @ts-ignore
          const token = resp.body.token;
          localStorage.setItem(AUTH_TOKEN, token);
          // update Observable User
          this.jwtToken$.next(token);
          return resp.body;
        }));
  }

  logout() {
    localStorage.removeItem(AUTH_TOKEN);
    this.jwtToken$.next(null);
  }

  isLogged(): boolean {
    return this.jwtToken$.getValue() !== null;
  }

  registration(formData: RegistrationForm): Observable<any> {
    return this.http.post(URL.REGISTRATION, formData);
  }

  getUserInfo(): Observable<any> {
    return this.http.get<User>(URL.USERS);
  }

  getUserById(id: number): Observable<User> {
    return this.http.get<User>(`${URL.USERS}${id}`);
  }

  putUser(user: User): Observable<User> {
    return this.http.put<User>(URL.USERS, user);
  }

  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(URL.USERS);
  }
}
