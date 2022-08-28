import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {URL} from "../constants";
import {Subscription} from "../model/subscription.model";
import {UserService} from "./user.service";

@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {

  constructor(private httpClient: HttpClient, private userService: UserService) { }

  // To get all the subscriptions with the provided user as SubscribedTo, so the users subscribed to the provided one
  getSubscribedToByUserId(userId: string): Observable<Subscription[]>{
    return this.httpClient.get<Subscription[]>(URL.SUBSCRIPTIONS + "/subscribedTo/" + userId);
  }

  // To get all the subscriptions with the provided user as Subscriber, so the users to which the provided one is subscribed
  getSubscribersByUserId(userId: string): Observable<Subscription[]>{
    return this.httpClient.get<Subscription[]>(URL.SUBSCRIPTIONS + "/subscriber/" + userId);
  }

  addSubscription(subscription: Subscription): Observable<Subscription> {
    return this.httpClient.post<Subscription>(URL.SUBSCRIPTIONS, subscription);
  }

  deleteSubscription(subscriptionId: string) {
    return this.httpClient.delete(URL.SUBSCRIPTIONS + '/' + subscriptionId)
  }
}
