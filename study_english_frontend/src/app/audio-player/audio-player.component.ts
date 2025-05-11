import { ChangeDetectionStrategy, Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { Audios } from '../common/audio';
import { UrlServiceService } from '../services/url-service.service';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'audio-player',
  templateUrl: './audio-player.component.html',
  styleUrls: ['./audio-player.component.scss']
})
export class AudioPlayerComponent implements OnInit {

  @ViewChild("audio")
  audio!: ElementRef<HTMLAudioElement>;

  @ViewChild('progressBar')
  progressBar!: ElementRef;

  @ViewChild('progress')
  progress!: ElementRef;

  @Input()
  audios: Audios[] = [];

  index: number = 0;
  selectedUrl: string = "";
  selectedTitle: string = "";
  paused: boolean = true;
  runtime: number = 0;
  progressWidth = '0%';
  isDragging = false;
  private isSeeking = false;
  private seekPosition = 0;

  constructor(private urlService: UrlServiceService, private cdRef: ChangeDetectorRef){}

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
      this.index++;
      this.onChangeIndex();
      this.paused = true;
    }
  }

  updateBar(event: Event) {
    if (!this.isSeeking) {
      const currentTime = this.audio.nativeElement.currentTime;
      const duration = this.audio.nativeElement.duration;
      const progressPercent = (currentTime / duration) * 100;

      this.progressWidth = progressPercent + '%';
      this.runtime = Math.floor(currentTime);
    }
  }

  startSeek(event: MouseEvent) {
    this.isSeeking = true;
    this.updateSeekPosition(event);
  }

  duringSeek(event: MouseEvent) {
    if (this.isSeeking) {
      this.updateSeekPosition(event);
    }
  }

  endSeek(event: MouseEvent) {
    if (this.isSeeking) {
      this.updateSeekPosition(event);
      this.audio.nativeElement.currentTime = this.seekPosition;
      this.isSeeking = false;

      if (!this.paused) {
        this.audio.nativeElement.play().catch(e => console.error(e));
      }
    }

  }

  private updateSeekPosition(event: MouseEvent) {
    const progressBar = this.progressBar.nativeElement;
    const progressBarRect = progressBar.getBoundingClientRect();
    const clickPosition = event.clientX - progressBarRect.left;
    const progressBarWidth = progressBarRect.width;
    const percentageClicked = Math.min(1, Math.max(0, clickPosition / progressBarWidth));

    this.seekPosition = percentageClicked * this.audio.nativeElement.duration;
    this.progressWidth = (percentageClicked * 100) + '%';
    this.runtime = Math.floor(this.seekPosition);
  }

  seek(event: MouseEvent) {
    if (!this.audio?.nativeElement) return;

    const progressBar = this.progressBar.nativeElement;
    const progressBarRect = progressBar.getBoundingClientRect();
    const clickPosition = event.clientX - progressBarRect.left;
    const progressBarWidth = progressBarRect.width;
    const percentageClicked = clickPosition / progressBarWidth;

    this.audio.nativeElement.currentTime = percentageClicked * this.audio.nativeElement.duration;

    this.progressWidth = (percentageClicked * 100) + '%';
  }



  startDrag() {
    this.isDragging = true;
  }

  duringDrag(event: MouseEvent) {
    if (this.isDragging) {
      this.seek(event);
    }
  }

  endDrag() {
    this.isDragging = false;
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
