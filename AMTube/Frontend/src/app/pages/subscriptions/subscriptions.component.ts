import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from "@angular/material/snack-bar";
import { Router } from '@angular/router';
import { USER_ID } from "../../constants";
import { Subscription } from "../../model/subscription.model";
import { SubscriptionService } from "../../services/subscription.service";

@Component({
  selector: 'app-subscriptions',
  templateUrl: './subscriptions.component.html',
  styleUrls: ['./subscriptions.component.css']
})
export class SubscriptionsComponent implements OnInit {
  mySubscribers: Subscription[] = [];
  mySubscribedTo: Subscription[] = [];
  mySubscribersReady: Boolean = false;
  mySubscribedToReady: Boolean = false;

  constructor(private subscriptionService: SubscriptionService, private matSnackBar: MatSnackBar, private router: Router) { }

  ngOnInit(): void {
    this.getMySubscribedTo();
    this.getMySubscriber();

  }

  getMySubscribedTo() {
    // @ts-ignore
    this.subscriptionService.getSubscribedToByUserId(localStorage.getItem(USER_ID)).subscribe(data => {
      this.mySubscribedTo = data;
      console.log(this.mySubscribedTo);
      this.mySubscribedToReady = true;

    }, ()=>{
      this.mySubscribedTo = [];
      this.mySubscribedToReady = true;

    })
  }

  getMySubscriber() {
    // @ts-ignore
    this.subscriptionService.getSubscribersByUserId(localStorage.getItem(USER_ID)).subscribe(data => {
      this.mySubscribers = data;
      console.log(this.mySubscribers);
      this.mySubscribersReady = true;
    }, ()=>{
      this.mySubscribers = [];
      this.mySubscribersReady = true;

    })
  }

  deleteSubscription(subscriptionId: number | undefined) {
    this.subscriptionService.deleteSubscription(String(subscriptionId)).subscribe(() => {
      this.matSnackBar.open("Subscription successfully deleted!", "OK");
    })
  }
  goToUser(userId: any) {
    let id = String(userId);
    this.router.navigateByUrl("/user-profile/" + id);
  }
}
