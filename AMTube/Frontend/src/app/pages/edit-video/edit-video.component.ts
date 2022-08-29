import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Video } from 'src/app/model/video.model';
import { UserService } from 'src/app/services/user.service';
import { VideoService } from 'src/app/services/video.service';
import { USER_ID } from 'src/app/constants';
import { FormControl, FormGroup } from "@angular/forms";
import { MatSnackBar } from "@angular/material/snack-bar";

@Component({
  selector: 'app-edit-video',
  templateUrl: './edit-video.component.html',
  styleUrls: ['./edit-video.component.css']
})
export class EditVideoComponent implements OnInit {
  video: Video;
  videoId!: string;
  ready = false;

  saveVideoDetailsForm: FormGroup;
  title: FormControl = new FormControl('');
  description: FormControl = new FormControl('');
  thumbnailSelectedFile!: File;
  thumbnailselectedFileName = '';
  thumbnailSelected = false;
  uploading = false;

  constructor(private activatedRoute: ActivatedRoute, private videoService: VideoService,
    private router: Router, private matSnackBar: MatSnackBar) {
    this.saveVideoDetailsForm = new FormGroup({
      title: this.title,
      description: this.description
    }
    )
  }

  ngOnInit(): void {
    this.videoId = this.activatedRoute.snapshot.params['videoId'];

    this.videoService.getVideo(this.videoId).subscribe(data => {
      this.video = data;
      if (this.video.publisherId = Number(localStorage.getItem(USER_ID))) {
        this.ready = true;
        console.log(this.video);
      }
    })
  }
  isFileAllowed(fileName: string, allowedFiles: string[]) {
    let isFileAllowed = false;
    const regex = /(?:\.([^.]+))?$/;
    const extension = regex.exec(fileName.toLowerCase());
    if (undefined !== extension && null !== extension) {
      for (const ext of allowedFiles) {
        if (ext === extension[0]) {
          isFileAllowed = true;
        }
      }
    }
    return isFileAllowed;
  }
  onFileSelected($event: Event) {
    // @ts-ignore
    let file = $event.target.files[0];
    if (this.isFileAllowed(file.name, ['.jpeg', ".png", "svg", ".jpg"])) {
      this.thumbnailSelectedFile = file;
      this.thumbnailselectedFileName = this.thumbnailSelectedFile.name;
      this.thumbnailSelected = true;
    }
    else {
      this.thumbnailselectedFileName = "";
      this.thumbnailSelected = false;
      this.matSnackBar.open("Only files in '.jpeg', '.jpg', '.png', 'svg' format are accepted for the Thumbnail.", "Ok");

    }
  }
  editVideo() {
    let id = String(this.video?.id);
    let title = this.video.title;
    let description = this.video.description;
    let message="";
    if (this.saveVideoDetailsForm.get('title')?.value != "") {
      title = this.saveVideoDetailsForm.get('title')?.value
      message="Title ";
    }
    if (this.saveVideoDetailsForm.get('description')?.value != "") {
      description = this.saveVideoDetailsForm.get('description')?.value
      if(message!=""){
        message= message+", ";
      }
      message=message+"Description ";
    }
    const videoMetadata: Video = {
      "title": title,
      "description": description,
      "publisherId": Number(localStorage.getItem(USER_ID)),
    }
    this.uploading = true;
    if (this.thumbnailSelected) {
      if(message!=""){
        message= message+", ";
      }
      message=message+"Thumbnail ";
      this.videoService.editThumbnail(this.thumbnailSelectedFile, id).subscribe(data => {
        this.videoService.editVideoMetadata(videoMetadata, id).subscribe(data => {
          this.video=data;
          this.matSnackBar.open(message+" edited successfully", "OK");
          this.uploading = false;
          console.log(this.video)
        });
    })
    }
    else{
      this.videoService.editVideoMetadata(videoMetadata, id).subscribe(data => {
        this.video=data;
        this.matSnackBar.open(message+" edited successfully", "OK");
        this.uploading = false;
        console.log(this.video)
      });
    }
    
  }
}
