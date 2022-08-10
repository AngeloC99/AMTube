import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
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
    PageNotFoundComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
