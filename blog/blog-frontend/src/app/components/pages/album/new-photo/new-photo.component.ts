import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PostsService } from '../../../../services/posts/posts.service';

@Component({
  selector: 'app-new-photo',
  templateUrl: './new-photo.component.html',
  styleUrls: ['./new-photo.component.scss']
})
export class NewPhotoComponent implements OnInit {

  newPhotoAlbumForm: FormGroup;
  selectedFile: File = null;
  userId: string;
  albumId: string;

  constructor(
    private formBuilder: FormBuilder,
    private postsService: PostsService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) { }

  ngOnInit() {
    this.userId = this.activatedRoute.snapshot.paramMap.get('userId');
    this.albumId = this.activatedRoute.snapshot.paramMap.get('albumId');
    this.newPhotoAlbumForm = this.formBuilder.group({
      photo: [null]
    });
  }

  onFileSelected(event) {
    this.selectedFile = <File>event.target.files[0];
  }

  get formControls() {
    return this.newPhotoAlbumForm.controls;
  }

  submit() {
    const { photo } = this.formControls;
    if (photo.value !== '') {
      const formData = new FormData()
      console.log(photo)
      formData.append('file', this.selectedFile, this.selectedFile.name);
      this.postsService.createFileAlbum(formData, this.albumId).subscribe((value) => {
        const route = `user/album/${this.userId}/${this.albumId}`;
        this.router.navigate([route]);
      });
    } else {
      photo.setErrors({'photo': 'Campo obrigatório'});
      return;
    }
  }
}
