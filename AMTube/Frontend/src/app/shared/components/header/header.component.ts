import {Component, OnInit} from '@angular/core';
import {UserService} from "../../../services/user.service";
import {Router} from "@angular/router";
import {Observable, of, switchMap} from "rxjs";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  logged$: Observable<boolean>;

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit(): void {
    this.logged$ = this.userService.getJwtToken().pipe(switchMap(jwt => {
      if (jwt) return of(true);
      return of(false);
    }));
  }

  onLogout() {
    this.userService.logout();
    this.router.navigateByUrl('login');
  }
  onLogin() {
    this.router.navigateByUrl('login');
  }
  onRegister() {
    this.router.navigateByUrl('register');
  }

  onHome(){
    this.logged$.subscribe(isLogged => {
      if(isLogged) {
        this.router.navigateByUrl('home')
      }
    })
  }

  onMyVideos() {
    this.router.navigateByUrl('my-videos');
  }

  onProfile() {
    this.router.navigateByUrl('my-profile');
  }

  onSubs() {
    this.router.navigateByUrl('subscriptions');
  }

  onNotifications() {
    this.router.navigateByUrl('notifications');
  }
}
