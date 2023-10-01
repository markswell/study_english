import { Component, Input, OnInit } from '@angular/core';
import { ReadCourseService } from '../services/read-course.service';
import { Video } from '../common/video';

@Component({
  selector: 'video-painel',
  templateUrl: './video-painel.component.html',
  styleUrls: ['./video-painel.component.scss']
})
export class VideoPainelComponent {

  @Input({ required: true })
  video: Video[] = [];

  passParam(video: Video): string {
    return video.url;
  }

}
