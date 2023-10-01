import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UrlServiceService {

  getBasePath(): string {
    return 'http://localhost:8081/study_english';
  }

  getBookListPath(): string {
    return '/path/book';
  }

  getVideoListPath(): string {
    return '/path/video';
  }

}
