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
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  userForm: FormGroup = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
  });

  submitted = false;
  user!: User;

  errorMessage = '';

  constructor(
    private router: Router,
    private authService: AuthService,
    private formBuilder: FormBuilder,
    private tokenStorage: TokenStorageService
  ) {}

  ngOnInit(): void {
    //form user
    this.userForm = this.formBuilder.group({
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
          Validators.minLength(5),
          Validators.maxLength(25),
        ],
      ],
    });

    this.userForm.reset();

    if (!this.submitted) {
      this.tokenStorage.signOut();
    }
  }

  onSubmit() {
    let dataResponse: any, usernameRequest: string, passwordRequest: any;

    usernameRequest = this.userForm.get(['username'])?.value;
    passwordRequest = this.userForm.get(['password'])?.value;

    this.authService.login(usernameRequest, passwordRequest).subscribe({
      next: (value) => {
        console.log('value ', value);
        dataResponse = value;
      },
      //todo implementer les erreurs de logs
      //redirection sur une page d'erreur

      complete: () => {
        this.submitted = true;
        console.log('complete dataResponse ', dataResponse);
        this.tokenStorage.saveToken(dataResponse.accessToken);
        this.tokenStorage.saveUser(dataResponse.username);
        this.resource();
      },
    });
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
