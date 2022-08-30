import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { USER_ID } from 'src/app/constants';
import { videoNotification } from 'src/app/model/videoNotification.model';
import { NotificationService } from 'src/app/services/notification.service';
import { UserService } from 'src/app/services/user.service';
import { VideoService } from 'src/app/services/video.service';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.css']
})
export class NotificationsComponent implements OnInit {
  notifications: videoNotification[] = [];
  videos = new Map();
  ready: Boolean = false;
  constructor(private router: Router, private videoService: VideoService, private userService: UserService,
    private notificationService: NotificationService) { }

  ngOnInit(): void {
    this.notificationService.getNotificationsByUserId(String(localStorage.getItem(USER_ID))).subscribe(data => {
      if (data) {
        this.notifications = data;
        console.log(this.notifications)
        this.notifications.forEach(notification => {
          this.videoService.getVideo(String(notification.videoId)).subscribe(data => {
            this.videos.set(notification.id, data);
            if ((this.videos.size == this.notifications.length)) {
              this.ready = true;
            }
          })
        });
      }
      else {
        this.notifications = [];
        this.ready = true;
        console.log("No Notifications")
      }
    }, () => {
      this.notifications = [];
      this.ready = true;
      console.log("No Notifications")
    })
  }
  goToVideo(videoId: Number) {
    this.router.navigateByUrl("/video-details/" + String(videoId));
  }

  goToUser(userId: Number) {
    this.router.navigateByUrl("/user-profile/" + String(userId));
  }
  delete(notificationId: any) {
    this.notificationService.markNotificationAsRead(String(notificationId)).subscribe(data => {
      this.notifications.splice(this.notifications.indexOf(data));
      this.videos.delete(Number(notificationId));
    })
  }
}
