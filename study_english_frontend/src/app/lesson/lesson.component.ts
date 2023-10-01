import { Component, OnChanges, OnDestroy, OnInit, SimpleChanges } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Audios } from '../common/audio';
import { LessonComplete } from '../common/lesson-complete';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-lesson',
  templateUrl: './lesson.component.html',
  styleUrls: ['./lesson.component.scss']
})
export class LessonComponent implements OnInit {

  audios: Audios[] = [];
  bookUrl: string = '';

  constructor(private route: ActivatedRoute, private router: Router, private sanitizer: DomSanitizer) {}

  ngOnInit(): void {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.route.queryParams.subscribe(p => {
      let lc: LessonComplete = JSON.parse(p['data']);
      this.audios = lc.audios;
      this.bookUrl = lc.bookUrl;
      console.log(this.bookUrl)
    });
  }

  getBook(): SafeResourceUrl {
    return this.sanitizer.bypassSecurityTrustResourceUrl( this.bookUrl );
  }

}
