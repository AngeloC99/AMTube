import { Injectable } from '@angular/core';
import {BehaviorSubject, map, Observable, take} from "rxjs";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {AUTH_TOKEN, URL, USER_ID} from "../constants";
import {LoginForm} from "../model/loginForm.model";
import {Token} from "../model/token.model";
import {RegistrationForm} from "../model/registrationForm.model";
import {User} from "../model/user.model";
import {tap} from "rxjs/operators";

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

  login(account: LoginForm) {
    return this.http.post<Token>(URL.LOGIN, account).pipe(
        tap((resp: Token) => {
          const token = resp.token;
          localStorage.setItem(AUTH_TOKEN, token);
          this.jwtToken$.next(token);
        }));
  }

  logout() {
    localStorage.removeItem(AUTH_TOKEN);
    localStorage.removeItem(USER_ID);
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
    return this.http.get<User>(`${URL.USERS}/${id}`);
  }

  putUser(user: User): Observable<User> {
    return this.http.put<User>(URL.USERS, user);
  }

  deleteUser(): Observable<any> {
    return this.http.delete<User>(URL.USERS+ "/" + localStorage.getItem(USER_ID));
  }

  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(URL.USERS);
  }
}
