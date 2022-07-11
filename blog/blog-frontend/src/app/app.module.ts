import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule }   from '@angular/forms';

import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatTabsModule } from '@angular/material/tabs';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import {MatRadioModule} from '@angular/material/radio';
import {MatSelectModule} from '@angular/material/select';
import {MatMenuModule} from '@angular/material/menu';
import {MatGridListModule} from '@angular/material/grid-list';

import { httpInterceptorsProvider } from './interceptors';

import { HeaderComponent } from './components/template/header/header.component';

import { HomeComponent } from './components/pages/home/home.component';
import { DashboardComponent } from './components/pages/dashboard/dashboard.component'
import { PostsComponent } from './components/pages/posts/posts.component';
import { PostComponent } from './components/pages/post/post.component';
import { LoginComponent } from './components/pages/login/login.component';
import { CommentComponent } from './components/pages/comment/comment.component';
import { AlbumsComponent } from './components/pages/albums/albums.component';
import { NewalbumComponent } from './components/pages/newalbum/newalbum.component';
import { AlbumComponent } from './components/pages/album/album.component';
import { NewPhotoComponent } from './components/pages/album/new-photo/new-photo.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    HomeComponent,
    DashboardComponent,
    PostsComponent,
    PostComponent,
    LoginComponent,
    CommentComponent,
    AlbumsComponent,
    NewalbumComponent,
    AlbumComponent,
    NewPhotoComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatTabsModule,
    MatIconModule,
    HttpClientModule,
    ReactiveFormsModule,
    MatListModule,
    MatRadioModule,
    MatSelectModule,
    MatMenuModule,
    MatGridListModule
    ],
  providers: [httpInterceptorsProvider],
  bootstrap: [AppComponent]
})
export class AppModule { }
