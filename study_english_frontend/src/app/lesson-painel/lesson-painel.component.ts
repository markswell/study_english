import { Component, Input, OnInit } from '@angular/core';
import { Lesson } from '../common/lesson';
import { LessonComplete } from '../common/lesson-complete';
import { UrlServiceService } from '../services/url-service.service';
import { ReadCourseService } from '../services/read-course.service';

@Component({
  selector: 'lesson-painel',
  templateUrl: './lesson-painel.component.html',
  styleUrls: ['./lesson-painel.component.scss']
})
export class LessonPainelComponent implements OnInit{
  
  @Input({ required: true })
  lessons?: Lesson[];

  @Input({ required: true })
  bookUrl: string = '';

  constructor(private resource: ReadCourseService) {}

  passParam(lesson: Lesson): string {
    let bookname = this.resource.getBook(this.bookUrl.replace(' ', "_"));
    let lc = new LessonComplete(lesson.audios, bookname);
    return JSON.stringify(lc);
  }

  ngOnInit(): void {
    console.log(this.bookUrl);
  }

}
