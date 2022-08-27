import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { MyVideosComponent } from './pages/my-videos/my-videos.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { HomeComponent } from './pages/home/home.component';
import { UserProfileComponent } from './pages/user-profile/user-profile.component';
import { NotificationsComponent } from './pages/notifications/notifications.component';
import { SubscriptionsComponent } from './pages/subscriptions/subscriptions.component';
import { VideoDetailComponent } from './pages/video-details/video-detail.component';
import { SearchResultsComponent } from './pages/search-results/search-results.component';
import { MyProfileComponent } from './pages/my-profile/my-profile.component';
import { UserInitialsAvatarComponent } from './shared/components/user-initials-avatar/user-initials-avatar.component';
import { PageNotFoundComponent } from './pages/page-not-found/page-not-found.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {NgxFileDropModule} from "ngx-file-drop";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatButtonModule} from "@angular/material/button";
import { HeaderComponent } from './shared/components/header/header.component'
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatIconModule} from "@angular/material/icon";
import {FlexLayoutModule} from "@angular/flex-layout";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatSelectModule} from "@angular/material/select";
import {MatOptionModule} from "@angular/material/core";
import {MatInputModule} from "@angular/material/input";
import {MatChipsModule} from "@angular/material/chips";
import {VgCoreModule} from "@videogular/ngx-videogular/core";
import {VgControlsModule} from "@videogular/ngx-videogular/controls";
import {VgBufferingModule} from "@videogular/ngx-videogular/buffering";
import {VgOverlayPlayModule} from "@videogular/ngx-videogular/overlay-play";
import { VideoPlayerComponent } from './shared/components/video-player/video-player.component';
import {MatSnackBar, MatSnackBarModule} from "@angular/material/snack-bar";
import {TokenInterceptorService} from "./services/token-interceptor.service";
import { FooterComponent } from './shared/components/footer/footer.component';
import { EditVideoComponent } from './pages/edit-video/edit-video.component';

@NgModule({
  declarations: [
    AppComponent,
    MyVideosComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    UserProfileComponent,
    NotificationsComponent,
    SubscriptionsComponent,
    VideoDetailComponent,
    SearchResultsComponent,
    MyProfileComponent,
    UserInitialsAvatarComponent,
    PageNotFoundComponent,
    HeaderComponent,
    VideoPlayerComponent,
    FooterComponent,
    EditVideoComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    NgxFileDropModule,
    MatButtonModule,
    MatFormFieldModule,
    MatToolbarModule,
    MatIconModule,
    MatSelectModule,
    MatOptionModule,
    FlexLayoutModule,
    ReactiveFormsModule,
    MatInputModule,
    MatChipsModule,
    VgCoreModule,
    VgControlsModule,
    VgOverlayPlayModule,
    VgBufferingModule,
    MatSnackBarModule
  ],
  providers: [{provide: HTTP_INTERCEPTORS, useClass: TokenInterceptorService, multi: true}],
  bootstrap: [AppComponent]
})
export class AppModule { }
