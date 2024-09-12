import { ChangeDetectionStrategy, Component, Input, OnInit } from '@angular/core';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { UrlServiceService } from '../services/url-service.service';

@Component({
  selector: 'book-painel',
  templateUrl: './book-painel.component.html',
  styleUrls: ['./book-painel.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class BookPainelComponent implements OnInit{

  @Input({ required: true })
  bookUrl: string = "";

  constructor(private sanitizer: DomSanitizer, private urlService: UrlServiceService) {}
  
  ngOnInit(): void {}

  getBook(): SafeResourceUrl {
    this.bookUrl = this.urlService.getBasePath() + this.bookUrl;
    this.bookUrl = this.bookUrl.replace(/\/[^\/]*$/, '/pdf');

    return this.sanitizer.bypassSecurityTrustResourceUrl( this.bookUrl );
  }
}
