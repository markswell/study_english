import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import  { SideContentComponent } from './side-content/side-content.component';
import { MatSidenavModule } from '@angular/material/sidenav';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatIconModule } from '@angular/material/icon';
import { HttpClientModule } from '@angular/common/http';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatListModule} from '@angular/material/list';
import { LessonPainelComponent } from './lesson-painel/lesson-painel.component';
import { LessonComponent } from './lesson/lesson.component';
import { VideoPainelComponent } from './video-painel/video-painel.component';
import { VideoPlayerComponent } from './video-player/video-player.component';
import { BookPainelModule } from './book-painel/book-painel.module';
import { AudioPlayerModule } from './audio-player/audio-player.module';

@NgModule({
    declarations: [
        AppComponent,
        SideContentComponent,
        LessonPainelComponent,
        LessonComponent,
        VideoPainelComponent,
        VideoPlayerComponent
    ],
    providers: [],
    bootstrap: [AppComponent],
    imports: [
        BrowserModule,
        AppRoutingModule,
        MatSidenavModule,
        BrowserAnimationsModule,
        MatIconModule,
        HttpClientModule,
        MatExpansionModule,
        MatListModule,
        BookPainelModule,
        AudioPlayerModule
    ]
})
export class AppModule { }
