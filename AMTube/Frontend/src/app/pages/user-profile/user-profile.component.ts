import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from "@angular/router";
import { UserService } from 'src/app/services/user.service';
import { USER_ID } from 'src/app/constants';
import { User } from 'src/app/model/user.model';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {
  userId!: string;

  constructor( private activatedRoute: ActivatedRoute, private userService: UserService) { 
    
    this.userId = this.activatedRoute.snapshot.params['userId'];
    console.log(this.userId);
  }

  ngOnInit(): void {
  }

}
