import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {URL} from "../constants";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class VideoService {

  constructor(private httpClient: HttpClient) { }

  uploadVideo(fileEntry: File): Observable<any> {
    const formData = new FormData()
    formData.append('file', fileEntry, fileEntry.name)   // same name "file" as request parameter in Video microservice

    // POST to upload the video
    return this.httpClient.post(URL.VIDEOS, formData);
    //return this.httpClient.post("http://localhost:8082/videos", formData);
  }
}
