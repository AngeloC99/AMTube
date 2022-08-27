import { Component, OnInit } from '@angular/core';
import { NgxFileDropEntry, FileSystemFileEntry, FileSystemDirectoryEntry } from 'ngx-file-drop';
import { VideoService } from "../../services/video.service";
import { Router } from "@angular/router";
import { FormControl, FormGroup } from "@angular/forms";
import { MatSnackBar } from "@angular/material/snack-bar";
import { Video } from "../../model/video.model";
import { USER_ID } from "../../constants";

@Component({
  selector: 'app-my-videos',
  templateUrl: './my-videos.component.html',
  styleUrls: ['./my-videos.component.css']
})
export class MyVideosComponent implements OnInit {

  //Video uploading variables
  public videofiles: NgxFileDropEntry[] = [];
  videoUploaded: boolean = false
  videofileEntry: FileSystemFileEntry | undefined
  uploading = false;
  //Thumbnail uploading variables
  saveVideoDetailsForm: FormGroup;
  title: FormControl = new FormControl('');
  description: FormControl = new FormControl('');
  thumbnailSelectedFile!: File;
  thumbnailselectedFileName = '';
  thumbnailSelected = false;

  //Myvideos Variables
  myVideos: Video[] = [];
  noVideos = true;
  constructor(private videoService: VideoService, private router: Router, private matSnackBar: MatSnackBar) {
    this.saveVideoDetailsForm = new FormGroup({
      title: this.title,
      description: this.description
    }
    )
  }
  ngOnInit(): void {
    this.getMyVideos();
  }
  public dropped(files: NgxFileDropEntry[]) {
    console.log(files);
    this.videofiles = files;
    for (const droppedFile of files) {

      // Is it a file?
      if (droppedFile.fileEntry.isFile && this.isFileAllowed(droppedFile.fileEntry.name, ['.mp4'])) {
        this.videofileEntry = droppedFile.fileEntry as FileSystemFileEntry;
        this.videofileEntry.file((file: File) => {
          this.videoUploaded = true;
          
        });
      } else {
        // It was a directory (empty directories are added, otherwise only files)
        this.matSnackBar.open("Only files in '.mp4' format are accepted for the Video.", "Ok");

        const fileEntry = droppedFile.fileEntry as FileSystemDirectoryEntry;
        this.videoUploaded = false;
        console.log(droppedFile.relativePath, fileEntry);
      }
    }
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
  public fileOver(event: any) {
    console.log(event);
  }

  public fileLeave(event: any) {
    console.log(event);
  }
  
  uploadVideo() {
    // Upload the video calling the backend
    this.uploading = true;
    this.videofileEntry?.file(file => {
      this.videoService.uploadVideo(file, this.thumbnailSelectedFile).subscribe(data => {
        console.log("Video uploaded successfully");
        this.saveVideoDetails(data.id)
      })
    })
  }
  saveVideoDetails(videoId: number | undefined) {
    let id = "";
    if (videoId) {
      id = videoId.toString();
    }
    const videoMetadata: Video = {
      "title": this.saveVideoDetailsForm.get('title')?.value,
      "description": this.saveVideoDetailsForm.get('description')?.value,
      "publisherId": Number(localStorage.getItem(USER_ID)),
    }
    this.videoService.saveVideo(videoMetadata, id).subscribe(data => {
      this.matSnackBar.open("Video uploaded successfully", "OK");
      this.uploading = false;
      this.myVideos.unshift(data);
    })
  }
  getMyVideos() {
    this.videoService.getVideosByUserId(String(localStorage.getItem(USER_ID))).subscribe(data => {
      this.myVideos = data;
      console.log(this.myVideos);
    })
    this.myVideos = [];
  }
  editVideo(videoId: number) {
    this.router.navigateByUrl("/edit-video/" + videoId);
  }
  deleteVideo(videoId: number) {
    this.videoService.deleteVideo(String(videoId)).subscribe(data => {
      let i = 0;
      for (let k = 0; k < this.myVideos.length; k++) {
        if (this.myVideos[k].id == videoId) {
          i = k;
        }
      }
      this.myVideos.splice(i,1);
    })
  }
  goToVideo(videoId: number) {
    this.router.navigateByUrl("/video-details/" + videoId);
  }
}
