import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from "@angular/router";
import { VideoService } from "../../services/video.service";
import { VideoComment } from "../../model/videoComment.model";
import { Video } from 'src/app/model/video.model';
import { UserService } from 'src/app/services/user.service';
import { USER_ID } from 'src/app/constants';
import { FormControl, FormGroup } from "@angular/forms";
import { User } from 'src/app/model/user.model';
import { Router } from "@angular/router";

@Component({
  selector: 'app-video-details',
  templateUrl: './video-detail.component.html',
  styleUrls: ['./video-detail.component.css']
})
export class VideoDetailComponent implements OnInit {
  videoId!: string;
  videoUrl!: string | undefined;
  videoAvailable: boolean = false;
  videoTitle: string | undefined;
  videoDescription!: string;
  videoDate: Date | undefined;
  videoComments: Array<VideoComment> | undefined;
  videoLikes!: Array<number> | undefined;
  likesCount!: number | undefined;
  publisherUsername!: string;
  publisherUserId!: Number;
  liked: boolean;
  newCommentForm: FormGroup;
  comment: FormControl = new FormControl('');

  currentUser: User;
  constructor(private activatedRoute: ActivatedRoute, private videoService: VideoService,
    private userService: UserService, private router: Router,) {

    this.newCommentForm = new FormGroup({
      comment: this.comment,
    }
    )
    this.videoId = this.activatedRoute.snapshot.params['videoId'];
    this.videoService.getVideo(this.videoId).subscribe(data => {
      this.videoUrl = data.videoUrl;
      this.videoTitle = data.title;
      this.videoDescription = data.description;
      this.videoComments = data.comments;
      console.log(this.videoComments);
      this.videoLikes = data.likes;
      this.likesCount = this.videoLikes?.length;
      this.videoDate = data.date;
      this.publisherUserId = data.publisherId;
      this.liked = this.videoLikes!.includes(Number(localStorage.getItem(USER_ID)));
      this.userService.getUserById(data.publisherId).subscribe(data => {
        this.publisherUsername = data.username;
        this.videoAvailable = true;
      });
    })

  }

  ngOnInit(): void {
    
    this.userService.getUserInfo().subscribe(data => {
      this.currentUser = data;
    });
  }

  like() {
    if (!this.liked) {
      this.videoService.likeVideo(this.videoId, String(localStorage.getItem(USER_ID))).subscribe(data => {
        this.liked = !this.liked;
        this.videoLikes?.push(data.userId);
        this.likesCount = this.videoLikes?.length;
      });
    }
    else {
      this.videoService.unlikeVideo(this.videoId, String(localStorage.getItem(USER_ID))).subscribe(data => {
        this.liked = !this.liked;
        var index = this.videoLikes?.indexOf(Number(localStorage.getItem(USER_ID)));
        if (index !== -1) {
          this.videoLikes?.splice(Number(index), 1);
        }
        this.likesCount = this.videoLikes?.length;
      });
    }
  }

  newComment() {
    const commentMetadata : VideoComment = {

      "publisherId": Number(localStorage.getItem(USER_ID)),
      "publisherUsername": this.currentUser.username,
      "text" : this.newCommentForm.get('comment')?.value,
      'videoId' : Number(this.videoId)
    };
    console.log(commentMetadata);
    
    this.videoService.addComment(String(this.videoId), commentMetadata).subscribe(data =>{
      console.log(data);
      this.videoComments?.unshift(data);
      console.log(this.videoComments);
    })
  }

  gotoUserProfile(userId: any){
    let id = String(userId);
    this.router.navigateByUrl("/user-profile/" + id);

  }
}
