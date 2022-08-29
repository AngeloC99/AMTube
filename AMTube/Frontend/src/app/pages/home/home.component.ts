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
  publishers = new Map();
  ready = false;
  constructor(private videoService: VideoService, private userService: UserService, private router: Router) { }

  ngOnInit(): void {
    this.userService.getUserInfo().subscribe(data => {
      localStorage.setItem(USER_ID, data.id)
    });
    this.videoService.getAllVideos().subscribe(data => {
      this.videos = data;
      this.videos.forEach(video =>{       
        this.userService.getUserById(video.publisherId).subscribe(data => {
          this.publishers.set((video.id), data);
          if((this.publishers.size==this.videos.length)){
            this.ready=true;
            console.log(this.publishers)
          }
        });
      })
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
