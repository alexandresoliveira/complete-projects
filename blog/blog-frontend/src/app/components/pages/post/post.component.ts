import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, FormControl } from '@angular/forms';

import { PostsService } from '../../../services/posts/posts.service';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.scss']
})
export class PostComponent implements OnInit {

  newPostForm: FormGroup;
  selectedFile: File = null;

  constructor(
    private formBuilder: FormBuilder,
    private postsService: PostsService,
    private router: Router
  ) { }

  ngOnInit() {
    this.newPostForm = this.formBuilder.group({
      type: ['text'],
      text: [''],
      link: [''],
      photo: [null]
    });
  }

  get formControls() {
    return this.newPostForm.controls;
  }

  onFileSelected(event) {
    this.selectedFile = <File>event.target.files[0];
  }

  submit() {
    const { type, text, link, photo } = this.formControls;
    if (type.value === 'text') {
      if (text.value !== '') {
        this.postsService.create({text: text.value}).subscribe((value) => {
          this.router.navigate(['dashboard']);
        });
      } else {
        text.setErrors({'text': 'Campo obrigatório'});
        return;
      }
    } else if (type.value === 'link') {
      if (link.value !== '') {
        this.postsService.create({text: link.value, link: link.value}).subscribe((value) => {
          this.router.navigate(['dashboard']);
        });
      } else {
        link.setErrors({'link': 'Campo obrigatório'});
        return;
      }
    } else {
      if (photo.value !== '') {
        const formData = new FormData()
        console.log(photo)
        formData.append('file', this.selectedFile, this.selectedFile.name);
        this.postsService.createFilePost(formData).subscribe((value) => {
          this.router.navigate(['dashboard']);
        });
      } else {
        photo.setErrors({'photo': 'Campo obrigatório'});
        return;
      }
    }
  }

}
