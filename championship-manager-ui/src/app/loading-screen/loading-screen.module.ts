import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoadingScreenComponent } from './loading-screen.component';
import {MatProgressBarModule} from "@angular/material/progress-bar";



@NgModule({
  declarations: [LoadingScreenComponent],
  exports: [
    LoadingScreenComponent
  ],
  imports: [
    CommonModule,
    MatProgressBarModule
  ]
})
export class LoadingScreenModule { }
