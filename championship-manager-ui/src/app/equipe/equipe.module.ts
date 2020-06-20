import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EquipeComponent } from './equipe.component';
import { EquipeListComponent } from './equipe-list/equipe-list.component';
import {EquipeFormComponent} from "./equipe-form/equipe-form.component";
import {MatTableModule} from "@angular/material/table";
import {MatSortModule} from "@angular/material/sort";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {MatPaginatorModule} from "@angular/material/paginator";
import {ReactiveFormsModule} from "@angular/forms";
import {MatCardModule} from "@angular/material/card";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatDialogModule} from "@angular/material/dialog";
import {MatSelectModule} from "@angular/material/select";



@NgModule({
  declarations: [EquipeComponent, EquipeListComponent, EquipeFormComponent],
    imports: [
        CommonModule,
        MatTableModule,
        MatButtonModule,
        MatSortModule,
        MatIconModule,
        MatPaginatorModule,
        ReactiveFormsModule,
        MatCardModule,
        MatFormFieldModule,
        MatInputModule,
        MatDialogModule,
        MatSelectModule
    ]
})
export class EquipeModule { }
