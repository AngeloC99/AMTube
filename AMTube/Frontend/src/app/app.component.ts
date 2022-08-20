import {Component, OnInit} from '@angular/core';
import {UserService} from "./services/user.service";
import {AUTH_TOKEN} from "./constants";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'frontend';

  constructor(private userService: UserService) {
  }

  ngOnInit() {
    const token = localStorage.getItem(AUTH_TOKEN);
    if(token) {
      this.userService.setJwtToken(token);
    }
  }
}
