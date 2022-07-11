import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PostsService } from 'src/app/services/posts/posts.service';
import { Album } from '../albums/models/Album';

@Component({
  selector: 'app-album',
  templateUrl: './album.component.html',
  styleUrls: ['./album.component.scss']
})
export class AlbumComponent implements OnInit {

  userId: string;
  albumId: string;
  album: Album;

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private postsService: PostsService
  ) { }

  ngOnInit() {
    this.userId = this.activatedRoute.snapshot.paramMap.get('userId');
    this.albumId = this.activatedRoute.snapshot.paramMap.get('albumId');
    this.postsService.album(this.userId, this.albumId).subscribe(value => {
      this.album = value;
    });
  }

  newPhotoAlbum(userId, idAlbum) {
    const route = `user/album/${userId}/${idAlbum}/new`;
    this.router.navigate([route]);
  }

}
