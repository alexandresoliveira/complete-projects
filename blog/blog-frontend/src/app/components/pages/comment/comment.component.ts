import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PostsService } from '../../../services/posts/posts.service';

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.scss']
})
export class CommentComponent implements OnInit {

  newCommentForm: FormGroup;
  idPost: string

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private formBuilder: FormBuilder,
    private postsService: PostsService
  ) { }

  ngOnInit() {
    this.idPost = this.activatedRoute.snapshot.paramMap.get('id');
    this.newCommentForm = this.formBuilder.group({
      text: ['', [Validators.required]]
    });
  }

  get formControls() {
    return this.newCommentForm.controls;
  }

  submit() {
    if (this.newCommentForm.invalid) return;
    const data = {postId: this.idPost, description: this.formControls.text.value};
    this.postsService.createComment(data).subscribe((value) => {
      this.router.navigate(['']);
    });
  }

}
