import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { PostsService } from '../../../services/posts/posts.service';
import { Page } from '../posts/models/Page';

import { Album } from './models/Album';

@Component({
  selector: 'app-albums',
  templateUrl: './albums.component.html',
  styleUrls: ['./albums.component.scss']
})
export class AlbumsComponent implements OnInit {

  idUser: string;
  albums: Page<Album>;

  constructor(
    private postsService: PostsService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) { }

  ngOnInit() {
    this.idUser = this.activatedRoute.snapshot.paramMap.get('id');
    this.postsService.albums(this.idUser).subscribe((data => {
      this.albums = data;
    }));
  }

  showAlbum(userId, idAlbum) {
    const route = `user/album/${userId}/${idAlbum}`;
    this.router.navigate([route]);
  }

  deleteAlbum(userId, idAlbum) {
    this.postsService.deleteAlbum(idAlbum).subscribe(value => {
      const index = this.albums.content.findIndex(album => album.id === idAlbum);
      this.albums.content.splice(index, 1);
    });
  }

}
