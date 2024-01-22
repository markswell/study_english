import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UrlServiceService {

  getBasePath(): string {
    return `${environment.API}:8081/study_english`;
  }

  getBookListPath(): string {
    return '/path/book';
  }

  getVideoListPath(): string {
    return '/path/video';
  }

}
