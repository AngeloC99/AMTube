import {MyVideosComponent} from "./pages/my-videos/my-videos.component";
import {HomeComponent} from "./pages/home/home.component";
import {PreloadAllModules, RouterModule, Routes} from "@angular/router";
import {LoginComponent} from "./pages/login/login.component";
import {MyProfileComponent} from "./pages/my-profile/my-profile.component";
import {VideoDetailComponent} from "./pages/video-details/video-detail.component";
import {UserProfileComponent} from "./pages/user-profile/user-profile.component";
import {NgModule} from "@angular/core";
import {SubscriptionsComponent} from "./pages/subscriptions/subscriptions.component";
import {PageNotFoundComponent} from "./pages/page-not-found/page-not-found.component";
import {RegisterComponent} from "./pages/register/register.component";
import {SearchResultsComponent} from "./pages/search-results/search-results.component";
import {SaveVideoDetailsComponent} from "./pages/save-video-details/save-video-details.component";
import {AuthGuard} from "./guard/auth.guard";
import {NotificationsComponent} from "./pages/notifications/notifications.component";

const routes: Routes = [
  {
    path: 'home', component: HomeComponent,
    canActivate: [AuthGuard],
  },
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full'
  },
  {
    path: 'login', component: LoginComponent,
  },
  {
    path: 'my-profile', component: MyProfileComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'my-videos', component: MyVideosComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'subscriptions', component: SubscriptionsComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'video-details/:videoId', component: VideoDetailComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'save-video-details/:videoId', component: SaveVideoDetailsComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'register', component: RegisterComponent,
  },
  {
    path: 'search', component: SearchResultsComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'user-profile/:userId', component: UserProfileComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'notifications', component: NotificationsComponent,
    canActivate: [AuthGuard],
  },
  {
    path: '**',
    component: PageNotFoundComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
