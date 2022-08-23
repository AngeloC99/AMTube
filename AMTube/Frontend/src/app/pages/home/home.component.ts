import { Component, OnInit } from '@angular/core';
import {VideoService} from "../../services/video.service";
import {UserService} from "../../services/user.service";
import {Router} from "@angular/router";
import {USER_ID} from "../../constants";
import { Video } from 'src/app/model/video.model';
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  videos = [1,2,3,4,5,6,7,8]
  constructor(private videoService: VideoService, private userService: UserService, private router: Router) { }

  ngOnInit(): void {
    //this.userService.getAllUsers().subscribe();
    this.userService.getUserInfo().subscribe(data => {
      localStorage.setItem(USER_ID, data.id)
    });
  }

  onLogout() {
    this.userService.logout();
    this.router.navigateByUrl('login');
  }
}
