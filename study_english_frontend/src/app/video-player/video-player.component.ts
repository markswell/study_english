import { Component,  OnInit } from '@angular/core';
import { UrlServiceService } from '../services/url-service.service';
import { ReadCourseService } from '../services/read-course.service';
import { Video } from '../common/video';
import { VideoModalComponent } from '../video-modal/video-modal.component';

@Component({
  selector: 'video-player',
  templateUrl: './video-player.component.html',
  styleUrls: ['./video-player.component.scss']
})
export class VideoPlayerComponent implements OnInit{

  classPath!: Video[];

  constructor(
    private path: UrlServiceService,
    private classPathService: ReadCourseService) {}

  ngOnInit(): void {
    this.classPathService.listVideos().subscribe(c => {
        this.classPath = c.map( cc => {
          cc.url = this.path.getBasePath() + cc.url;
          return cc;
        })
    })
  }

  getThumbailUrl(url: string): string {
    console.log(url + '/thumbnail');
    return url + '/thumbnail';
  }

}
