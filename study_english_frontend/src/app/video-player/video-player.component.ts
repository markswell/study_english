import { Component,  OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UrlServiceService } from '../services/url-service.service';

@Component({
  selector: 'video-player',
  templateUrl: './video-player.component.html',
  styleUrls: ['./video-player.component.scss']
})
export class VideoPlayerComponent implements OnInit{

  url!: string;

  constructor(private route: ActivatedRoute, private path: UrlServiceService) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(p => {
      this.url = this.path.getBasePath() + p['data'];
      console.log(this.url);
    });
  }

}
