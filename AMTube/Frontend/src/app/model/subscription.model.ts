import {Time} from "@angular/common";

export interface Subscription {
  id?: number;
  subscriberId: number;
  subscriber: string;
  subscribedToId: number;
  subscribedTo: string;
  date?: Date;
  time?: Time;
}
