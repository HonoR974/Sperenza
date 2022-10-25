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
          [
            null,
            Validators.required,
            Validators.minLength(6),
            Validators.maxLength(20),
          ],
        ],
        password: [
          [
            null,
            Validators.required,
            Validators.minLength(6),
            Validators.maxLength(20),
          ],
        ],
        confirmPassword: [
          [
            null,
            Validators.required,
            Validators.minLength(6),
            Validators.maxLength(20),
          ],
        ],
        email: [[null, Validators.required, Validators.email]],
        acceptTerms: [[false, Validators.requiredTrue]],
      },
      {
        validators: [Validation.match('password', 'confirmPassword')],
      }
    );

    this.userForm.reset();
  }

  onSubmit(): void {
    let dataResponse: any, username: string, password: string, email: string;

    username = this.userForm.get(['username'])?.value;
    password = this.userForm.get(['password'])?.value;
    email = this.userForm.get(['email'])?.value;

    this.authService.register(username, email, password).subscribe({
      next: (value) => {
        console.log('value register ', value);
        dataResponse = value;
      },
      error: (err) => {
        console.error('erreur register ' + err);
        //a ameliorer
        this.router.navigate(['error']);
      },
      complete: () => {
        this.submitted = true;
        console.log('complete dataResponse ', dataResponse);
        this.tokenStorage.saveToken(dataResponse.accessToken);
        this.tokenStorage.saveUser(dataResponse.username);
        this.resource();
      },
    });

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

  resource() {
    this.router.navigate(['resource/list']);
  }
}
