import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import { ReadCourseService } from '../services/read-course.service';
import { Book } from '../common/book';

@Component({
  selector: 'side-content',
  templateUrl: './side-content.component.html',
  styleUrls: ['./side-content.component.scss']
})
export class SideContentComponent implements OnInit{

  step = 0;
  isLesson: boolean = true;
  books: Book[] = [];

  constructor(private service: ReadCourseService) {}
  
  ngOnInit(): void {
    this.service.listBooks().subscribe(res => {
      this.books = res;
    });
  }
  
  setStep(index: number) {
    this.step = index;
  }
  
}
