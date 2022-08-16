import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-user-initials-avatar',
  templateUrl: './user-initials-avatar.component.html',
  styleUrls: ['./user-initials-avatar.component.css']
})
export class UserInitialsAvatarComponent implements OnInit {

  @Input('user') user: any;

  constructor() { }

  ngOnInit(): void {
  }

}
