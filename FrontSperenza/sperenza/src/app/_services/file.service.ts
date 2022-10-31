import { Injectable } from '@angular/core';
import * as AWS from 'aws-sdk/global';
import * as S3 from 'aws-sdk/clients/s3';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { FileApi } from '../_class/file-api';
import { Observable } from 'rxjs';

const File_API = 'http://localhost:8081/api/file/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
};
@Injectable({
  providedIn: 'root',
})
export class FileService {
  constructor(private http: HttpClient) {}

  uploadFileS3(file: any, filePath: any) {
    return new Promise((resolve, reject) => {
      const contentType = file.type;
      const bucket = new S3({
        accessKeyId: 'AKIA3JCVUBIEPQAIYJNR', // ACCESS_KEY_ID
        secretAccessKey: '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', // SECRET_ACCESS_KEY
        region: 'eu-west-3', // BUCKET_REGION
      });

      const params = {
        Bucket: 'sperenza', //BUCKET_NAME
        Key: filePath,
        Body: file,
        ACL: 'public-read',
        ContentType: contentType,
      };

      bucket.upload(params, (err: any, data: any) => {
        if (err) {
          reject(err);
        } else {
          resolve(data);
        }
      });
    });
  }

  uploadFileAPI(file: FileApi): Observable<any> {
    return this.http.post(File_API, file);
  }

  getAllFileAPI(): Observable<FileApi[]> {
    return this.http.get<FileApi[]>(File_API);
  }
}
