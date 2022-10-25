import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';

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
import { authInterceptorProviders } from './_helpers/AuthInterceptor';
import { ErrorComponent } from './_component/error/error.component';
import { CreateComponent } from './_component/resource/create/create.component';
import { CorpsComponent } from './_component/resource/corps/corps.component';

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
    ErrorComponent,
    CreateComponent,
    CorpsComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  providers: [authInterceptorProviders],
  bootstrap: [AppComponent],
})
export class AppModule {}
