import { Component, OnInit } from '@angular/core';
import { AUTH_TOKEN } from "../../../constants";
import { UserService } from "../../../services/user.service";
import { Router } from "@angular/router";
import { FormControl, FormGroup } from '@angular/forms';
import { VideoService } from 'src/app/services/video.service';
import { Video } from 'src/app/model/video.model';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  logged: boolean = false;
  queryFormGroup: FormGroup;
  searchQuery: FormControl = new FormControl('');
  searching: boolean = false;
  constructor(private userService: UserService, private router: Router, private videoService: VideoService) {
    this.queryFormGroup = new FormGroup({
      searchQuery: this.searchQuery
    })
  }

  ngOnInit(): void {

  }

  ngAfterViewChecked() {
    if (localStorage.getItem(AUTH_TOKEN) !== 'auth-token' && localStorage.getItem(AUTH_TOKEN) !== null) {
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

  onHome() {
    if (this.logged) {
      this.router.navigateByUrl('home')
    }
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

  search() {
    let query = this.queryFormGroup.get("searchQuery")?.value;
    console.log('Search query: ' + query);
    this.searching = true;
    this.videoService.search(query).subscribe(data => {
      let videos: Video[] = data;
      console.log(videos);
      this.searching = false;
      this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => this.router.navigate(['search'], {
        queryParams: { query: query },
        state: { "videos": videos }
      }));

    });

  }
}
