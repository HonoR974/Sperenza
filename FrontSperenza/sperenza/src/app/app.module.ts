import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MenuComponent } from './_component/menu/menu.component';
import { HomeComponent } from './_component/home/home.component';
import { PresentationComponent } from './_component/presentation/presentation.component';
import { ListComponent } from './_component/resource/list/list.component';
import { LoginComponent } from './_component/logs/login/login.component';
import { RegisterComponent } from './_component/logs/register/register.component';
import { FormsModule } from '@angular/forms';
import { ProfileComponent } from './_component/profile/profile.component';

@NgModule({
  declarations: [
    AppComponent,
    MenuComponent,
    HomeComponent,
    PresentationComponent,
    ListComponent,
    LoginComponent,
    RegisterComponent,
    ProfileComponent,
  ],
  imports: [BrowserModule, AppRoutingModule, HttpClientModule, FormsModule],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
