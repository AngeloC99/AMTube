import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {URL} from "../constants";
import {videoNotification} from "../model/videoNotification.model";

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor(private httpClient: HttpClient) { }

  // To get all the notifications received by a user
  getNotificationsByUserId(userId: string): Observable<videoNotification[]>{
    return this.httpClient.get<videoNotification[]>(URL.NOTIFICATIONS + "/receiver/" + userId);
  }

  // To mark as read the notification, simply pass the notification id, then the backend will do it
  markNotificationAsRead(notificationId: string): Observable<videoNotification>{
    return this.httpClient.delete<videoNotification>(URL.NOTIFICATIONS +"/"+ notificationId);
  }

}
