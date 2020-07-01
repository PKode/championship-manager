import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ChampionnatComponent} from "./championnat.component";
import {ChampionnatListComponent} from "./championnat-list/championnat-list.component";
import {ChampionnatFormComponent} from "./championnat-form/championnat-form.component";
import {MatTableModule} from "@angular/material/table";
import {MatButtonModule} from "@angular/material/button";
import {MatSortModule} from "@angular/material/sort";
import {MatIconModule} from "@angular/material/icon";
import {MatPaginatorModule} from "@angular/material/paginator";
import {ReactiveFormsModule} from "@angular/forms";
import {MatCardModule} from "@angular/material/card";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatDialogModule} from "@angular/material/dialog";
import { ChampionnatDetailComponent } from './championnat-detail/championnat-detail.component';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatMenuModule } from '@angular/material/menu';
import { LayoutModule } from '@angular/cdk/layout';
import { CalendrierComponent } from './calendrier/calendrier.component';
import {MatListModule} from "@angular/material/list";
import {MatSelectModule} from "@angular/material/select";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatNativeDateModule} from "@angular/material/core";


@NgModule({
  declarations: [
    ChampionnatComponent,
    ChampionnatListComponent,
    ChampionnatFormComponent,
    ChampionnatDetailComponent,
    CalendrierComponent
  ],
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
    MatGridListModule,
    MatMenuModule,
    LayoutModule,
    MatListModule,
    MatSelectModule,
    MatDatepickerModule,
    MatNativeDateModule
  ]
})
// @ts-ignore
export class ChampionnatModule {
}
