import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TransfertComponent } from './transfert/transfert.component';
import {MatGridListModule} from "@angular/material/grid-list";
import {MatListModule} from "@angular/material/list";
import { MatCardModule } from '@angular/material/card';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { LayoutModule } from '@angular/cdk/layout';
import {MatSelectModule} from "@angular/material/select";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatOptionModule} from "@angular/material/core";
import {JoueurModule} from "../joueur/joueur.module";



@NgModule({
  declarations: [TransfertComponent],
  imports: [
    CommonModule,
    MatGridListModule,
    MatListModule,
    MatCardModule,
    MatMenuModule,
    MatIconModule,
    MatButtonModule,
    MatSelectModule,
    MatFormFieldModule,
    MatOptionModule,
    LayoutModule,
    JoueurModule
  ]
})
export class TransfertModule { }
