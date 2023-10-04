import { Component, Input } from '@angular/core';
import { Lesson } from '../common/lesson';
import { ReadCourseService } from '../services/read-course.service';

@Component({
  selector: 'lesson-painel',
  templateUrl: './lesson-painel.component.html',
  styleUrls: ['./lesson-painel.component.scss']
})
export class LessonPainelComponent{
  
  @Input({ required: true })
  lessons?: Lesson[];

  @Input({ required: true })
  bookUrl: string = '';

  constructor(private resource: ReadCourseService) {}

  passParam(lesson: Lesson): string {
    return JSON.stringify(lesson.audios);
  }

  passBookUrl(): string {
    return this.resource.getBook(this.bookUrl.replace(' ', "_"));
  }

}
