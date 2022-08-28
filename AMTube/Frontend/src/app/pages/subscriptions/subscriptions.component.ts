import { Component, OnInit } from '@angular/core';
import {MatSnackBar} from "@angular/material/snack-bar";
import {USER_ID} from "../../constants";
import {Subscription} from "../../model/subscription.model";
import {SubscriptionService} from "../../services/subscription.service";

@Component({
  selector: 'app-subscriptions',
  templateUrl: './subscriptions.component.html',
  styleUrls: ['./subscriptions.component.css']
})
export class SubscriptionsComponent implements OnInit {
  mySubscribers: Subscription[] = [];
  mySubscribedTo: Subscription[] = [];

  constructor(private subscriptionService: SubscriptionService, private matSnackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.getMySubscribedTo();
    this.getMySubscriber()

  }

  getMySubscribedTo() {
    // @ts-ignore
    this.subscriptionService.getSubscribedToByUserId(localStorage.getItem(USER_ID)).subscribe(data => {
      this.mySubscribedTo = data;
      console.log(this.mySubscribedTo);
    })
  }

  getMySubscriber() {
    // @ts-ignore
    this.subscriptionService.getSubscribersByUserId(localStorage.getItem(USER_ID)).subscribe(data => {
      this.mySubscribers = data;
      console.log(this.mySubscribers);
    })
  }

  deleteSubscription(subscriptionId: number | undefined) {
    this.subscriptionService.deleteSubscription(String(subscriptionId)).subscribe(() => {
      this.matSnackBar.open("Subscription successfully deleted!", "OK");
    })
  }

}
