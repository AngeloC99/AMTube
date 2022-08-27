import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../../services/user.service";
import {Router} from "@angular/router";
import {HttpErrorResponse} from "@angular/common/http";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registrationForm!: FormGroup;

  constructor(private userService: UserService,
              private formBuilder: FormBuilder,
              private router: Router,
              private matSnackBar: MatSnackBar) { }

  ngOnInit() {
    this.registrationForm = this.formBuilder.group({
      username: ['', [
        Validators.required,
        Validators.maxLength(20),
        Validators.minLength(5)
      ]],
      email: ['', [
        Validators.required,
        Validators.email
      ]],
      password: ['', [
        Validators.required,
        Validators.maxLength(30),
        Validators.minLength(5)
      ]]
    });
  }

  onRegister() {
    this.userService.registration(this.registrationForm.value).subscribe(() => {
          this.registrationForm.reset();
          this.router.navigateByUrl('login');
        },
        (err: HttpErrorResponse) => {
            console.error('Registration request error: ' + err.status);
            this.matSnackBar.open("ERROR: this user already exists!", "OK");
            this.registrationForm.reset();
        });
  }


  goLogin() {
    this.router.navigateByUrl('login')
  }
}
