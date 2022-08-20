import {MyVideosComponent} from "./pages/my-videos/my-videos.component";
import {HomeComponent} from "./pages/home/home.component";
import {PreloadAllModules, RouterModule, Routes} from "@angular/router";
import {LoginComponent} from "./pages/login/login.component";
import {MyProfileComponent} from "./pages/my-profile/my-profile.component";
import {VideoDetailComponent} from "./pages/video-detail/video-detail.component";
import {UserProfileComponent} from "./pages/user-profile/user-profile.component";
import {NgModule} from "@angular/core";
import {SubscriptionsComponent} from "./pages/subscriptions/subscriptions.component";
import {PageNotFoundComponent} from "./pages/page-not-found/page-not-found.component";
import {RegisterComponent} from "./pages/register/register.component";
import {SearchResultsComponent} from "./pages/search-results/search-results.component";
import {SaveVideoDetailsComponent} from "./pages/save-video-details/save-video-details.component";

const routes: Routes = [
  {
    path: 'home', component: HomeComponent,
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
  },
  {
    path: 'my-videos', component: MyVideosComponent,
  },
  {
    path: 'subscriptions', component: SubscriptionsComponent,
  },
  {
    path: 'video-detail/:videoId', component: VideoDetailComponent,
  },
  {
    path: 'save-video-details/:videoId', component: SaveVideoDetailsComponent,
  },
  {
    path: 'register', component: RegisterComponent,
  },
  {
    path: 'search-results', component: SearchResultsComponent,
  },
  {
    path: 'user-profile', component: UserProfileComponent,
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
