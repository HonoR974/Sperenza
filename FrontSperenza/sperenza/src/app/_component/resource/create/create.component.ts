import { Component, OnInit } from '@angular/core';
import { FileApi } from 'src/app/_class/file-api';
import { FileService } from 'src/app/_services/file.service';

@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css'],
})
export class CreateComponent implements OnInit {
  files: File[] = [];

  fileAPI!: FileApi;

  listFileAPI: FileApi[] = [];

  constructor(private fileService: FileService) {}

  ngOnInit() {}

  onSelect(event: any) {
    this.files.push(...event.addedFiles);
  }

  onRemove(event: any) {
    this.files.splice(this.files.indexOf(event), 1);
  }

  async onImageUpdate() {
    for (let i = 0; i < this.files.length; i += 1) {
      let file = this.files[i];

      let filePath = 'img/' + file.name; // to create unique name for avoiding being replaced

      let name: string = file.name;
      try {
        //s3
        let response = await this.fileService.uploadFileS3(file, filePath);
        console.log(response);

        //API
        this.fileAPI.name = name;
        this.fileAPI.url = filePath;
        this.fileService.uploadFileAPI(this.fileAPI).subscribe({
          next: (value) => {
            console.log('next value ' + value);
          },
          complete: () => {
            window.location.reload();
          },
        });
      } catch (error) {}
    }
    this.files = [];
  }

  getAllFileAPI() {
    this.fileService.getAllFileAPI().subscribe({
      next: (value) => {
        this.listFileAPI = value;
        console.log('value get All FILE API ' + value);
      },
    });
  }
}
