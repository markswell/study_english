import { ChangeDetectionStrategy, Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { Audios } from '../common/audio';
import { UrlServiceService } from '../services/url-service.service';

@Component({
  selector: 'audio-player',
  templateUrl: './audio-player.component.html',
  styleUrls: ['./audio-player.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AudioPlayerComponent implements OnInit {

  @ViewChild("progress")
  progress!: ElementRef;

  @ViewChild("audio")
  audio!: ElementRef<HTMLAudioElement>;

  @Input()
  audios: Audios[] = [];
  index: number = 0;
  selectedUrl: string = "";
  selectedTitle: string = "";
  paused: boolean = true;
  runtime: number = 0;

  constructor(private urlService: UrlServiceService){}

  ngOnInit(): void {
    this.index = 0;
    this.onChangeIndex();
  }

  play() {
    this.audio.nativeElement.play();
    this.paused = false;
  }

  pause() {
    this.audio.nativeElement.pause();
    this.paused = true;
  }

  selectNext() {
    if(this.audios.length >= this.index) {
      console.log('selectNext')
      this.index++;
      this.onChangeIndex();
      this.paused = true;
    }
  }

  updateBar(event: Event) {
    this.progress.nativeElement.style.width = (Math.floor((this.audio.nativeElement.currentTime / this.audio.nativeElement.duration) * 100)) + '%';
    this.runtime = Math.floor(this.audio.nativeElement.currentTime);
  }

  selectPrevius() {
    console.log('selectPrevius')
    if(this.index > 0) {
      console.log('selectPrevius')
      this.index--;
      this.onChangeIndex();
      this.paused = true;
    }
  }

  getSelected(): string {
    return this.urlService.getBasePath() + this.selectedUrl;
  }

  onChangeIndex() {
    this.selectedUrl = this.audios[this.index].url;
    this.selectedTitle = this.audios[this.index].title;
    this.progress.nativeElement.style.width = 0;
  }

  getSoundLenght(): string {
    try{
      return this.convertToMinutes(this.audio.nativeElement.duration);
    } catch(err) {
      return '0:00';
    }
  }

  convertToMinutes(seconds: number): string {
    let min: any = (seconds % 3600) / 60;
    let secs: any = (seconds % 3600) % 60;
    min = Math.floor(min);
    secs = Math.floor(secs) < 10? '0' + Math.floor(secs) : Math.floor(secs);
    if(!min) {
      min = '0';
    }
    if(!secs) {
      secs = '00';
    }
    return min + ":" + secs;
  }

}
