import { Component, OnInit } from '@angular/core';
import {AUTH_TOKEN} from "../../../constants";
import {UserService} from "../../../services/user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  logged: boolean = false;

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit(): void {

  }

  ngAfterViewChecked() {
    if(localStorage.getItem(AUTH_TOKEN) !== 'auth-token' && localStorage.getItem(AUTH_TOKEN) !== null){
      console.log(AUTH_TOKEN);
      this.logged = true;
    }
    else this.logged = false;
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
