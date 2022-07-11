import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/authentication/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  showNewAlbumButton: boolean;

  constructor(
    private router: Router,
    private authService: AuthService
  ) { }

  ngOnInit() {
    this.showNewAlbumButton = true;
  }

  albumUser() {
    const routeId = `/user/albums/new`;
    this.router.navigate([routeId]);
  }

  signOut() {
    this.authService.signOut();
    this.router.navigate([]);
  }

}
