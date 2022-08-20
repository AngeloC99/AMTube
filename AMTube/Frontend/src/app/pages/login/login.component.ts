import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {UserService} from "../../services/user.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginFormModel!: FormGroup;


  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private userService: UserService) { }

  ngOnInit() {
    this.loginFormModel = this.formBuilder.group({
      username: ['', [
        Validators.required,
        Validators.maxLength(20),
        Validators.minLength(5)
      ]],
      password: ['', [
        Validators.required,
        Validators.maxLength(30),
        Validators.minLength(5)
      ]]
    });
  }

  onLogin() {
    this.userService.login(this.loginFormModel.value).subscribe(() => {
          this.loginFormModel.reset();
          this.router.navigateByUrl('home');
        },
        (err: HttpErrorResponse) => {
          if (err.status === 401) {
            console.error('Login request error: ' + err.status);
            // SHOW ERROR

          }
        });
  }


  goRegister() {
    this.router.navigateByUrl('register');
  }
}
