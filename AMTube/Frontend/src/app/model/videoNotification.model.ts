import {Time} from "@angular/common";

export interface videoNotification {
  id?: number;
  videoId: number;
  videoTitle: string;
  publisherId: number;
  publisherUsername: string;
  date: Date;
  time: Time;
}
