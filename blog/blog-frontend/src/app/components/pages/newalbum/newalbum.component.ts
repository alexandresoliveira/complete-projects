import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { PostsService } from 'src/app/services/posts/posts.service';

@Component({
  selector: 'app-newalbum',
  templateUrl: './newalbum.component.html',
  styleUrls: ['./newalbum.component.scss']
})
export class NewalbumComponent implements OnInit {

  newAlbumForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private postsService: PostsService,
    private router: Router
  ) { }

  ngOnInit() {
    this.newAlbumForm = this.formBuilder.group({
      title: ['', [Validators.required]]
    });
  }

  get formControls() {
    return this.newAlbumForm.controls;
  }

  submit() {
    if (this.newAlbumForm.invalid) return;
    this.postsService.newAlbum(this.formControls.title.value).subscribe((value) => {
      this.router.navigate(['']);
    });
  }

}
