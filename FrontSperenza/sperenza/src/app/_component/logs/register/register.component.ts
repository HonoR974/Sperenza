import { Component, OnInit } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
  FormArray,
} from '@angular/forms';
import { Router } from '@angular/router';
import Validation from 'src/app/utils/Validation';
import { User } from 'src/app/_class/user';
import { AuthService } from 'src/app/_services/auth.service';
import { TokenStorageService } from 'src/app/_services/token-storage.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  userForm: FormGroup = new FormGroup({
    username: new FormControl(''),
    email: new FormControl(''),
    password: new FormControl(''),
    confirmPassword: new FormControl(''),
    acceptTrerms: new FormControl(false),
  });

  submitted = false;
  user!: User;

  constructor(
    private router: Router,
    private authService: AuthService,
    private formBuilder: FormBuilder,
    private tokenStorage: TokenStorageService
  ) {}

  ngOnInit(): void {
    //form user
    this.userForm = this.formBuilder.group(
      {
        username: [
          '',
          Validators.required,
          Validators.minLength(6),
          Validators.maxLength(20),
        ],
        password: [
          '',
          Validators.required,
          Validators.minLength(5),
          Validators.maxLength(25),
        ],
        confirmPassword: [
          '',
          Validators.required,
          Validators.minLength(5),
          Validators.maxLength(25),
        ],
        email: ['', Validators.required, Validators.email],
        acceptTerms: [false, Validators.requiredTrue],
      },
      {
        validators: [Validation.match('password', 'confirmPassword')],
      }
    );
  }

  onSubmit(): void {
    const username = this.userForm.get(['username']);

    //const password = this.f['password'];

    let token: any, data: any;

    if (false) {
      this.authService.login(token, data).subscribe({
        next(data) {
          console.log('data ' + data);
          console.log('token ' + data.jwt);
          token = data.jwt;
          data = data;
        },
        error(er) {
          console.log('erreur ' + er);
        },
      });
    }

    this.submitted = true;

    if (this.userForm.invalid) {
      return;
    }

    console.log(JSON.stringify(this.userForm.value, null, 2));
  }

  get f(): { [key: string]: AbstractControl } {
    return this.userForm.controls;
  }

  reloadPage(): void {
    window.location.reload();
  }

  onReset(): void {
    this.submitted = false;
    this.userForm.reset();
  }

  accueil() {
    this.router.navigate(['home']);
  }
}
