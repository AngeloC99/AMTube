import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {VideoService} from "../../services/video.service";
import {Comment} from "../../model/comment.model";

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
  videoComments: Array<Comment> | undefined;
  videoLikes!: Array<number> | undefined;
  likesCount!: number | undefined;

  constructor(private activatedRoute: ActivatedRoute,
              private videoService: VideoService) {
    this.videoId = this.activatedRoute.snapshot.params['videoId'];
    this.videoService.getVideo(this.videoId).subscribe(data => {
      this.videoUrl = data.videoUrl;
      this.videoTitle = data.title;
      this.videoDescription = data.description;
      this.videoComments = data.comments;
      this.videoLikes = data.likes;
      this.likesCount = this.videoLikes?.length;
      this.videoDate = data.date;
      this.videoAvailable = true;
    })
  }

  ngOnInit(): void {
  }

}
