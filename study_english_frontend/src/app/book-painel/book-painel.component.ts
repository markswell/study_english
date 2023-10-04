import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'book-painel',
  templateUrl: './book-painel.component.html',
  styleUrls: ['./book-painel.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class BookPainelComponent implements OnInit{

  bookUrl: string = "";

  constructor(private route: ActivatedRoute, private sanitizer: DomSanitizer) {}
  
  ngOnInit(): void {
    this.bookUrl = this.route.snapshot.queryParams['bookUrl'];
  }

  getBook(): SafeResourceUrl {
    return this.sanitizer.bypassSecurityTrustResourceUrl( this.bookUrl );
  }
}
