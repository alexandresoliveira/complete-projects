import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { PostsService } from '../../../services/posts/posts.service';

import { Page } from './models/Page';
import { Post } from './models/Post';
import { Comment } from './models/Comment';

export interface CommentDialogData {
  description: string;
}

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.scss']
})
export class PostsComponent implements OnInit {

  posts: Page<Post>;

  constructor(
    private postsService: PostsService,
    private router: Router
  ) { }

  ngOnInit() {
    this.postsService.index().subscribe((data => {
      this.posts = data;
    }));
  }

  remove(post: Post) {
    this.postsService.delete(post.id).subscribe(() => {
      this.onDeletePost(post);
    });
  }

  removeComment(post: Post, comment: Comment) {
    this.postsService.deleteComment(post.id, comment.id).subscribe(() => {
      this.onDeletePostComment(post, comment);
    });
  }

  onDeletePost(post: Post) {
    if (post) {
      const index = this.posts.content.findIndex((postItem) => postItem.id === post.id);
      this.posts.content.splice(index, 1);
    }
  }

  onDeletePostComment(post: Post, comment: Comment) {
    if (post) {
      const index = this.posts.content.findIndex((postItem) => postItem.id === post.id);
      const indexComment = this.posts.content[index].comments.findIndex((commentItem) => commentItem.id === comment.id);
      this.posts.content[index].comments.splice(indexComment, 1);
    }
  }

  editPost(id) {
    const routeId = `/post/${id}/comment`;
    this.router.navigate([routeId]);
  }

  albumUser(id) {
    const routeId = `/user/${id}/albums`;
    this.router.navigate([routeId]);
  }

}
