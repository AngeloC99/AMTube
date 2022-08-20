import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../../services/user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registrationForm!: FormGroup;

  constructor(private userService: UserService,
              private formBuilder: FormBuilder,
              private router: Router) { }

  ngOnInit() {
    this.registrationForm = this.formBuilder.group({
      username: ['', [
        Validators.required,
        Validators.maxLength(20),
        Validators.minLength(5)
      ]],
      email: ['', [
        Validators.required,
        Validators.maxLength(25),
        Validators.minLength(5)
      ]],
      password: ['', [
        Validators.required,
        Validators.maxLength(30),
        Validators.minLength(5)
      ]]
    });
  }

  onRegister(): void {
    this.userService.registration(this.registrationForm.value).subscribe(() =>{
      this.registrationForm.reset();
      this.router.navigateByUrl('login');
    },
        (error => console.error(error))
    );
  }

  goLogin() {
    this.router.navigateByUrl('login')
  }
}
