import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Resource } from '../_class/resource';
import { TokenStorageService } from './token-storage.service';

const BASE_URL = 'http://localhost:8081/api/resource/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
};

@Injectable({
  providedIn: 'root',
})
export class ResourceService {
  constructor(private http: HttpClient) {}

  getAllResource(): Observable<Resource[]> {
    return this.http.get<Resource[]>(BASE_URL + 'all', httpOptions);
  }
}
