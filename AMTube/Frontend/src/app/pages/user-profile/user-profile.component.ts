import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { UserService } from 'src/app/services/user.service';
import {AUTH_TOKEN, USER_ID} from 'src/app/constants';
import { User } from 'src/app/model/user.model';
import { Video } from 'src/app/model/video.model';
import { VideoService } from 'src/app/services/video.service';
import {SubscriptionService} from "../../services/subscription.service";
import {Subscription} from "../../model/subscription.model";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {
  userReady: boolean = false;
  viewedUser: User;
  videos: Video[] = [];
  videosReady: boolean = false;
  subscription: Subscription;
  subscribed: boolean;
  constructor(private activatedRoute: ActivatedRoute, private userService: UserService,
    private videoService: VideoService, private router: Router, private subscriptionService: SubscriptionService) {
  }

  ngOnInit(): void {
    this.userService.getUserById(Number(this.activatedRoute.snapshot.params['userId'])).subscribe(data => {
      this.viewedUser = data;
      console.log(this.viewedUser);
      this.userReady = true;
      this.videoService.getVideosByUserId(this.activatedRoute.snapshot.params['userId']).subscribe(data => {
        this.videos=data;
        console.log(this.videos);
        this.videosReady = true;
      });
      this.subscriptionService.checkSubscription(String(localStorage.getItem(USER_ID)),String(data.id)).subscribe(data=>{
          console.log(data);
        if(data){
          this.subscribed=true;
          this.subscription=data;
        }
        else{
          this.subscribed=false;
        }
      })
    });
  }

  subscribe() {
    
    this.userService.getUserInfo().subscribe(data=>{
       const subscriptionMetaData: Subscription = {
      "subscribedToId": Number(this.viewedUser.id),
      "subscribedTo": String(this.viewedUser.username),
      "subscriberId": Number(localStorage.getItem(USER_ID)),
      "subscriber":String(data.username)
    }
    if(!this.subscribed){
      this.subscriptionService.addSubscription(subscriptionMetaData).subscribe(data=>{
        this.subscription=data;
        this.subscribed=true;
      });
    }
    });
   
  }
  unsubscribe(){
    if(this.subscribed){
      this.subscriptionService.deleteSubscription(String(this.subscription.id)).subscribe(data=>{
        this.subscribed=false;
      });
    };
  }

  goToVideo(videoArrayIndex: number) {
    this.router.navigateByUrl("/video-details/" + this.videos[videoArrayIndex].id);
  }
}
