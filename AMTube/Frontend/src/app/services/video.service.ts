import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {URL} from "../constants";
import {Observable} from "rxjs";
import {Video} from "../model/video.model";

@Injectable({
  providedIn: 'root'
})
export class VideoService {

  constructor(private httpClient: HttpClient) { }

  uploadVideo(fileEntryVideo: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', fileEntryVideo, fileEntryVideo.name);   // same name "file" as request parameter in Video microservice
    return this.httpClient.post<Video>(URL.VIDEOS, formData);
  }


  uploadThumbnail(fileEntry: File, videoId: string): Observable<any> {
    const formData = new FormData();
    formData.append('thumbnail', fileEntry, fileEntry.name);
    formData.append('videoId', videoId);
    console.log(URL.VIDEOS + "/" + videoId + "/thumbnails");
    return this.httpClient.post<Video>(URL.THUMBNAILS, formData);
  }

  getVideo(videoId: String): Observable<Video>{
    return this.httpClient.get<Video>(URL.VIDEOS + "/" + videoId);
  }

  saveVideo(videoMetaData: Video, videoId: string): Observable<Video> {
    return this.httpClient.put<Video>(URL.VIDEOS + "/" + videoId, videoMetaData);
  }
}
