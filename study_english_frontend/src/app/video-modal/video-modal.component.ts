import { Component, ElementRef, Inject, Input, ViewChild } from '@angular/core';
import { Dialog } from '@angular/cdk/dialog';

@Component({
  selector: 'video-modal',
  templateUrl: './video-modal.component.html',
  styleUrls: ['./video-modal.component.scss']
})
export class VideoModalComponent {

  @ViewChild('myDialog') private modal?: ElementRef<HTMLDialogElement>;
  @ViewChild('videoPlayer') private videoPlayer?: ElementRef<HTMLVideoElement>;
  @Input() url!: string;
  dialog!: Dialog;

  VideoModalComponent(dialog: Dialog){
    this.dialog = dialog;
  }
    
  getThumbailUrl(url: string): string {
    return url + '/thumbnail';
  }

  closeModal() {
    this.modal!.nativeElement.close();
    this.videoPlayer!.nativeElement.pause();
  }

  openVideo(): void {
    
    this.modal!.nativeElement.showModal();
  }

}
