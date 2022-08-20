import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {ActivatedRoute} from "@angular/router";
import {VideoService} from "../../services/video.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Video} from "../../model/video.model";

@Component({
  selector: 'app-save-video-details',
  templateUrl: './save-video-details.component.html',
  styleUrls: ['./save-video-details.component.css']
})
export class SaveVideoDetailsComponent implements OnInit {

  saveVideoDetailsForm: FormGroup;
  title: FormControl = new FormControl('');
  description: FormControl = new FormControl('');
  selectedFile!: File;
  selectedFileName = '';
  videoId = '';
  fileSelected = false;
  videoUrl!: string | undefined;

  constructor(private activatedRoute: ActivatedRoute, private videoService: VideoService, private matSnackBar: MatSnackBar) {
    this.videoId = this.activatedRoute.snapshot.params['videoId'];
    this.videoService.getVideo(this.videoId).subscribe(data => {
      this.videoUrl = data.videoUrl;
    })
    this.saveVideoDetailsForm = new FormGroup({
      title: this.title,
      description: this.description
      }
    )
  }

  ngOnInit(): void {
  }


  onFileSelected($event: Event) {
    // @ts-ignore
    this.selectedFile = $event.target.files[0];
    this.selectedFileName = this.selectedFile.name;
    this.fileSelected = true;
  }

  onUpload() {
    this.videoService.uploadThumbnail(this.selectedFile, this.videoId).subscribe( data => {
      console.log("Hello");
      this.matSnackBar.open("Thumbnail Uploaded Successfully", "OK");
    })
  }

  saveVideo() {
    const videoMetadata: Video = {
      "title": this.saveVideoDetailsForm.get('title')?.value,
      "description": this.saveVideoDetailsForm.get('description')?.value,
      "publisherId": 12,
    }
    this.videoService.saveVideo(videoMetadata, this.videoId).subscribe(data => {
      this.matSnackBar.open("Video Metadata Updated successfully", "OK");
    })
  }
}
