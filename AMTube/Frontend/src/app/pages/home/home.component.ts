import { Component, OnInit } from '@angular/core';
import { VideoService } from "../../services/video.service";
import { UserService } from "../../services/user.service";
import { Router } from "@angular/router";
import { USER_ID } from "../../constants";
import { Video } from 'src/app/model/video.model';
import { User } from 'src/app/model/user.model';
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  videos: Video[] = []
  publishers: User[] = []
  ready = false;
  constructor(private videoService: VideoService, private userService: UserService, private router: Router) { }

  ngOnInit(): void {
    this.userService.getUserInfo().subscribe(data => {
      localStorage.setItem(USER_ID, data.id)
    });
    this.videoService.getAllVideos().subscribe(data => {
      this.videos = data;
      console.log(this.videos);
      for (let k = 0; k < this.videos.length; k++) {
        this.userService.getUserById(this.videos[k].publisherId).subscribe(data => {
          this.publishers.push(data)
          if (this.publishers.length == this.videos.length) {
            console.log(this.publishers);
            this.ready = true;
          }
        });
      }

    });
  }

  onLogout() {
    this.userService.logout();
    this.router.navigateByUrl('login');
  }

  goToVideo(videoArrayIndex: number) {
    this.router.navigateByUrl("/video-details/" + this.videos[videoArrayIndex].id);
  }
}
