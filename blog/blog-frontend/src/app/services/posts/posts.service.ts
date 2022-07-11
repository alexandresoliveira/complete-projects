import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Album } from 'src/app/components/pages/albums/models/Album';
import { environment } from 'src/environments/environment';

import { Page } from '../../components/pages/posts/models/Page';
import { Post } from '../../components/pages/posts/models/Post';

interface CreateComment {
  postId: string;
  description: string;
}

interface CreatePost {
  text: string;
  link?: string;
}

@Injectable({
  providedIn: 'root'
})
export class PostsService {

  constructor(
    private http: HttpClient
  ) { }

  index() {
    return this.http.get<Page<Post>>(`${environment.urlApi}/v1/posts`);
  }

  createComment(comment: CreateComment) {
    return this.http.post<any>(`${environment.urlApi}/v1/posts/${comment.postId}/comments`, {description: comment.description});
  }

  delete(postId: string) {
    return this.http.delete(`${environment.urlApi}/v1/posts/${postId}`);
  }

  deleteComment(postId: string, commentId: string) {
    return this.http.delete(`${environment.urlApi}/v1/posts/${postId}/comments/${commentId}`);
  }

  create(post: CreatePost) {
    return this.http.post(`${environment.urlApi}/v1/posts`, post);
  }

  createFilePost(post: FormData) {
    return this.http.post(`${environment.urlApi}/v1/posts`, post);
  }

  newAlbum(title: string) {
    return this.http.post(`${environment.urlApi}/v1/photo/collection`, {title})
  }

  albums(idUser) {
    return this.http.get<Page<Album>>(`${environment.urlApi}/v1/photo/collection/${idUser}`);
  }

  album(userId, albumId) {
    return this.http.get<Album>(`${environment.urlApi}/v1/photo/collection/${userId}/${albumId}`);
  }

  deleteAlbum(idAlbum: string) {
    return this.http.delete(`${environment.urlApi}/v1/photo/collection/${idAlbum}`);
  }

  createFileAlbum(photo: FormData, idAlbum: string) {
    return this.http.post(`${environment.urlApi}/v1/photo/collection/photo/${idAlbum}`, photo);
  }
}
