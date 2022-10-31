import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { FileApi } from 'src/app/_class/file-api';
import { FileService } from 'src/app/_services/file.service';
import { TokenStorageService } from 'src/app/_services/token-storage.service';

@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css'],
})
export class CreateComponent implements OnInit {
  files: File[] = [];
  fileApi: FileApi = new FileApi();

  listFileAPI: FileApi[] = [];

  renderImages: any = [];
  cheminImage: any = 'https://sperenza.s3.eu-west-3.amazonaws.com/';

  isLoading: boolean = false;
  constructor(
    private fileService: FileService,
    private toaster: ToastrService,
    private tokenStorage: TokenStorageService
  ) {}

  ngOnInit() {
    this.getAllFileAPI();
  }

  onSelect(event: any) {
    this.files.push(...event.addedFiles);
  }

  onRemove(event: any) {
    this.files.splice(this.files.indexOf(event), 1);
  }

  async onImageUpdate() {
    console.log('this files ', this.files);
    if (this.files.length < 1) {
      this.toaster.error('Please Select Drop your Image first');
      return;
    }

    for (let i = 0; i < this.files.length; i += 1) {
      let file = this.files[i];

      let filePath = 'images/' + file.name;

      try {
        this.isLoading = true;
        let response = await this.fileService.uploadFileS3(file, filePath);
        console.log('response ', response);

        this.toaster.success(file.name + 'uploaded Successfully :)');
        const url = (response as any).Location;
        this.renderImages.push(url);
      } catch (error) {
        this.toaster.error('Something went wrong! ');
      }

      this.fileApi = new FileApi();

      this.fileApi.name = file.name;
      this.fileApi.url = this.cheminImage + filePath;
      console.log('file API ', this.fileApi);
      this.saveFileAPI(this.fileApi);
    }
    this.files = [];
  }

  saveFileAPI(file: FileApi) {
    console.log('save File API ', file);

    this.fileService.uploadFileAPI(file).subscribe({
      next: (value) => {
        console.log('value ', value);
      },
      error: (err) => {
        console.log('err ', err);
      },
      complete: () => {
        console.log('complete ');
        window.location.reload();
      },
    });
  }

  getAllFileAPI() {
    this.fileService.getAllFileAPI().subscribe({
      next: (value) => {
        this.listFileAPI = value;
        console.log('value get All FILE API ', value);
      },
    });
  }
}
