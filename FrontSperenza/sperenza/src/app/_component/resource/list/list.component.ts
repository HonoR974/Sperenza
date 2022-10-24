import { Component, OnInit } from '@angular/core';
import { Resource } from 'src/app/_class/resource';
import { User } from 'src/app/_class/user';
import { ResourceService } from 'src/app/_services/resource.service';
import { TokenStorageService } from 'src/app/_services/token-storage.service';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css'],
})
export class ListComponent implements OnInit {
  isLoggedIn = false;
  user!: User;
  resourceList: Resource[] = [];

  constructor(
    private tokenStorage: TokenStorageService,
    private resourceService: ResourceService
  ) {}

  ngOnInit(): void {
    if (this.tokenStorage.getToken()) {
      console.log('token in list  ', this.tokenStorage.getToken());

      this.user = this.tokenStorage.getUser();
      this.isLoggedIn = true;
      this.getResource();
    } else {
      console.log('Disconnected in list ');
    }
  }

  getResource() {
    let response: any;
    this.resourceService.getAllResource().subscribe({
      next: (value) => {
        response = value;
        console.log('value list ', value);
      },
      error: (er) => {
        console.error('erreur ', er);
      },
      complete: () => {
        console.log('complete ');
        this.resourceList = response;
      },
    });
  }
}
