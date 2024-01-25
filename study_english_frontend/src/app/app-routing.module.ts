import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LessonComponent } from './lesson/lesson.component';
import { ExplaningComponent } from './explaning/explaning.component'
import { VideoPlayerComponent } from './video-player/video-player.component';

const routes: Routes = [
  { path: "lesson", component:  LessonComponent },
  { path: "classes", component:  VideoPlayerComponent },
  { path: "", component:  ExplaningComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
