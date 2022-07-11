import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { AuthService } from 'src/app/services/authentication/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  signInForm: FormGroup;
  signUpForm: FormGroup;

  constructor(
    private authService: AuthService,
    private router: Router,
    private formBuilder: FormBuilder
  ) { }

  ngOnInit() {
    this.signInForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
    this.signUpForm = this.formBuilder.group({
      name: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  get signInFormControls() {
    return this.signInForm.controls;
  }

  get signUpFormControls() {
    return this.signUpForm.controls;
  }

  async signInSubmit() {
    if (this.signInForm.invalid) { 
      return; 
    };
    try {
      await this.authService.signIn(this.signInForm.value);
      this.router.navigate(['dashboard']);
    } catch (error) {
      console.log(error);
    }
  }

  async signUpSubmit() {
    if (this.signUpForm.invalid) return;
    try {
      const data = await this.authService.signUp(this.signUpForm.value);
      if (data) {
        this.router.navigate(['dashboard']);
      }
    } catch (error) {
      console.log(error);
    }
  }

}
