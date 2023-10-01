import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Book } from '../common/book';
import { UrlServiceService } from './url-service.service';
import { Video } from '../common/video';

@Injectable({
  providedIn: 'root'
})
export class ReadCourseService {

  
  constructor(private http: HttpClient, private urlService: UrlServiceService) { }

  listBooks() {
    return this.http.get<Book[]>(this.urlService.getBasePath() + this.urlService.getBookListPath());
  }

  listVideos() {
    return this.http.get<Video[]>(this.urlService.getBasePath() + this.urlService.getVideoListPath());
  }

  getBook(bookName: string): string {
    return this.urlService.getBasePath() + "/media/" + bookName;
  }

}
