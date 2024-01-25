import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import { ReadCourseService } from '../services/read-course.service';
import { Book } from '../common/book';
import { Video } from '../common/video';

@Component({
  selector: 'side-content',
  templateUrl: './side-content.component.html',
  styleUrls: ['./side-content.component.scss']
})
export class SideContentComponent implements OnInit, AfterViewInit{

  step = 0;
  isLesson: boolean = true;
  books: Book[] = [];
  vid: Video[] = [];


  
  constructor(private service: ReadCourseService) {}
  ngAfterViewInit(): void {
    this.service.listVideos().subscribe(e => {
      this.vid = e;
    })
  }
  
  ngOnInit(): void {
    this.service.listBooks().subscribe(res => {
      this.books = res;
    });
  }
  
  setStep(index: number) {
    this.step = index;
  }
  
}
