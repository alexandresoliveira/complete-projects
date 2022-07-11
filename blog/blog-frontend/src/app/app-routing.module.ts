import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardComponent } from './components/pages/dashboard/dashboard.component';

import { HomeComponent } from './components/pages/home/home.component';
import { LoginComponent } from './components/pages/login/login.component';
import { PostComponent } from './components/pages/post/post.component';
import { PostsComponent } from './components/pages/posts/posts.component';
import { CommentComponent } from './components/pages/comment/comment.component';

import { AuthGuard } from './guards/authentication/auth.guard';
import { AlbumsComponent } from './components/pages/albums/albums.component';
import { NewalbumComponent } from './components/pages/newalbum/newalbum.component';
import { AlbumComponent } from './components/pages/album/album.component';
import { NewPhotoComponent } from './components/pages/album/new-photo/new-photo.component';


const routes: Routes = [
  {
    path: '',
    component: DashboardComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: '',
        component: PostsComponent,
      },
      {
        path: 'post/new',
        component: PostComponent,
      },
      {
        path: 'post/:id/comment', 
        component: CommentComponent,
      },
      {
        path: 'user/:id/albums', 
        component: AlbumsComponent,
      },
      {
        path: 'user/album/:userId/:albumId', 
        component: AlbumComponent,
      },
      {
        path: 'user/album/:userId/:albumId/new', 
        component: NewPhotoComponent,
      },
      {
        path: 'user/albums/new', 
        component: NewalbumComponent,
      }
    ]
  },
  {
    path: '',
    component: HomeComponent,
    children: [
      {
        path: '',
        redirectTo: 'login',
        pathMatch: 'full'
      },
      {
        path: 'login',
        component: LoginComponent
      }
    ]
  },
  { 
    path: '**', 
    redirectTo: '' 
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
