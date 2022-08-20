import { Component, OnInit } from '@angular/core';
import {VideoService} from "../../services/video.service";
import {UserService} from "../../services/user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private videoService: VideoService, private userService: UserService, private router: Router) { }

  ngOnInit(): void {
    this.userService.getAllUsers().subscribe();
  }

  onLogout() {
    this.userService.logout();
    this.router.navigateByUrl('login');
  }
}
