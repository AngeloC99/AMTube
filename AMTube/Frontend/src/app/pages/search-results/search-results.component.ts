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
  query: string;
  users: User[] = []
  constructor(private router: Router, private videoService: VideoService, private userService: UserService) {
    this.query = this.router.getCurrentNavigation()?.initialUrl?.queryParams?.['query']
    if (this.router.getCurrentNavigation()?.extras?.state?.['videos']) {
      this.videos = this.router.getCurrentNavigation()?.extras?.state?.["videos"];
      for (let k = 0; k < this.videos.length; k++) {
        this.userService.getUserById(this.videos[k].publisherId).subscribe(data => {
          this.users.push(data)
          if (this.users.length == this.videos.length) {
            console.log(this.users);
            console.log(this.videos);
            this.searchReady = true;
          }
        });
      }
      
    }
    else {
      this.videoService.search(this.query).subscribe(data => {
        this.videos = data;
        console.log(this.videos);
        for (let k = 0; k < this.videos.length; k++) {
          this.userService.getUserById(this.videos[k].publisherId).subscribe(data => {
            this.users.push(data)
            if (this.users.length == this.videos.length) {
              console.log(this.users);
              console.log(this.videos);
              this.searchReady = true;
            }
          });
        }
      })
    }
  }

  ngOnInit(): void {
  }
  goToVideo(videoArrayIndex: number) {
    this.router.navigateByUrl("/video-details/" + this.videos[videoArrayIndex].id);
  }
}
