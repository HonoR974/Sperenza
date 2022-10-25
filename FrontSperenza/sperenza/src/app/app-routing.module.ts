import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './_component/home/home.component';
import { PresentationComponent } from './_component/presentation/presentation.component';
import { ListComponent } from './_component/resource/list/list.component';
import { LoginComponent } from './_component/logs/login/login.component';
import { RegisterComponent } from './_component/logs/register/register.component';
import { ProfileComponent } from './_component/profile/profile.component';
import { ErrorComponent } from './_component/error/error.component';

const routes: Routes = [
  //Accueil
  { path: 'home', component: HomeComponent },
  { path: '', redirectTo: 'home', pathMatch: 'full' },

  //Présentation
  { path: 'presentation', component: PresentationComponent },

  //Resource
  { path: 'resource/list', component: ListComponent },

  //Logs
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },

  //Profile
  { path: 'profile', component: ProfileComponent },

  //error
  { path: 'error', component: ErrorComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
