import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/model/user.model';
import { Video } from 'src/app/model/video.model';
import { UserService } from 'src/app/services/user.service';
import { VideoService } from 'src/app/services/video.service';

@Component({
  selector: 'app-search-results',
  templateUrl: './search-results.component.html',
  styleUrls: ['./search-results.component.css']
})
export class SearchResultsComponent implements OnInit {
  searchReady = false;
  videos: Video[];
  queryText: string = "";

  publishers = new Map();
  constructor(private router: Router, private videoService: VideoService, private userService: UserService) {
  }

  ngOnInit(): void {
    this.queryText = String(this.router.url.substring(14));
    if (this.router.getCurrentNavigation()?.extras?.state?.['videos']) {
      this.videos = this.router.getCurrentNavigation()?.extras?.state?.["videos"];

      this.videos.forEach(video => {
        this.userService.getUserById(video.publisherId).subscribe(data => {
          this.publishers.set((video.id), data);
          if ((this.publishers.size == this.videos.length)) {
            this.searchReady = true;
          }
        });
      })
    }
    else {
      this.videoService.search(this.queryText).subscribe(data => {
        this.videos = data;
        console.log(this.videos);
        this.videos.forEach(video => {
          this.userService.getUserById(video.publisherId).subscribe(data => {
            this.publishers.set((video.id), data);
            if ((this.publishers.size == this.videos.length)) {
              this.searchReady = true;
            }
          });
        })
      })
    }
  }
  goToVideo(videoArrayIndex: number) {
    this.router.navigateByUrl("/video-details/" + this.videos[videoArrayIndex].id);
  }
  goToUser(userId: any) {
    let id = String(userId);
    this.router.navigateByUrl("/user-profile/" + id);
  }
}
