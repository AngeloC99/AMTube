import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {URL, URL_BASE} from "../constants";
import {Observable} from "rxjs";
import {Video} from "../model/video.model";
import { VideoComment } from '../model/videoComment.model';

@Injectable({
  providedIn: 'root'
})
export class VideoService {

  constructor(private httpClient: HttpClient) { }

  uploadVideo(fileEntryVideo: File, fileEntryThumbnail: File): Observable<Video>{
    const formData = new FormData();
    formData.append('videoFile', fileEntryVideo, fileEntryVideo.name);
    formData.append('thumbnail', fileEntryThumbnail, fileEntryThumbnail.name);
    return this.httpClient.post<Video>(URL.VIDEOS, formData);
  }
  editVideo(fileEntryVideo: File, videoId: string): Observable<any> {
    const formData = new FormData();
    formData.append('videoFile', fileEntryVideo, fileEntryVideo.name);   // same name "file" as request parameter in Video microservice
    return this.httpClient.post<Video>(URL.VIDEO + "/" + videoId, formData);
  }


  editThumbnail(fileEntry: File, videoId: string): Observable<any> {
    const formData = new FormData();
    formData.append('thumbnail', fileEntry, fileEntry.name);
    console.log(URL.VIDEOS + "/thumbnails"+ "/" + videoId );
    return this.httpClient.put<Video>(URL.THUMBNAILS + "/" + videoId, formData);
  }

  getVideo(videoId: String): Observable<Video>{
    return this.httpClient.get<Video>(URL.VIDEOS + "/" + videoId);
  }

  saveVideo(videoMetaData: Video, videoId: string): Observable<Video> {
    return this.httpClient.put<Video>(URL.VIDEOS + "/" + videoId, videoMetaData);
  }
  getVideosByUserId(userId: String): Observable<Video[]>{
    return this.httpClient.get<Video[]>(URL.VIDEOS_BY_USER_ID + "/" + userId);
  }
  deleteVideo(videoId: string): Observable<Video>{
    return this.httpClient.delete<Video>(URL.VIDEOS + "/" + videoId);
  }
  getAllVideos(): Observable<Video[]>{
    return this.httpClient.get<Video[]>(URL.VIDEOS);
  }
  likeVideo(videoId: string, userId: string): Observable<any>{
    return this.httpClient.post<Video>(URL.VIDEOS+"/"+videoId+"/"+URL.LIKE+'/'+userId,null);
  }
  unlikeVideo(videoId: string, userId: string): Observable<any>{
    return this.httpClient.delete<Video>(URL.VIDEOS+"/"+videoId+"/"+URL.LIKE+'/'+userId);
  }
  addComment(videoId: string, commentMetadata: VideoComment): Observable<VideoComment>{
    return this.httpClient.post<VideoComment>(URL.VIDEOS+"/"+videoId+"/"+URL.COMMENTS, commentMetadata);
  }
  search(query: string): Observable<Video[]>{
    return this.httpClient.get<Video[]>(URL.VIDEOS);
  }
}
