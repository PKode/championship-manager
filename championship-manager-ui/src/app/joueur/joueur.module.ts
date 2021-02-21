import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {JoueurComponent} from './joueur.component';
import {MatTableModule} from '@angular/material/table';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatSortModule} from '@angular/material/sort';
import {JoueurListComponent} from './joueur-list/joueur-list.component';
import {MatButtonModule} from "@angular/material/button";
import {JoueurFormComponent} from './joueur-form/joueur-form.component';
import {MatInputModule} from '@angular/material/input';
import {MatSelectModule} from '@angular/material/select';
import {MatRadioModule} from '@angular/material/radio';
import {MatCardModule} from '@angular/material/card';
import {ReactiveFormsModule} from '@angular/forms';
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatIconModule} from "@angular/material/icon";
import {MatSnackBarModule} from "@angular/material/snack-bar";


// @ts-ignore
@NgModule({
  declarations: [JoueurComponent, JoueurListComponent, JoueurFormComponent],
  exports: [
    JoueurListComponent
  ],
  imports: [
    CommonModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatButtonModule,
    MatInputModule,
    MatSelectModule,
    MatRadioModule,
    MatCardModule,
    ReactiveFormsModule,
    MatDatepickerModule,
    MatIconModule,
    MatSnackBarModule
  ]
})
export class JoueurModule {
}
