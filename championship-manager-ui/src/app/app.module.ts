import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {SidenavComponent} from './sidenav/sidenav.component';
import {LayoutModule} from '@angular/cdk/layout';
import {GraphQLModule} from './graphql.module';
import {HttpClientModule} from '@angular/common/http';
import {ConfirmDialogComponent} from "./confirm-dialog/confirm-dialog.component";
import {ChampionnatModule} from "./championnat/championnat.module";
import {MatDialogModule} from "@angular/material/dialog";
import {MatButtonModule} from "@angular/material/button";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatListModule} from "@angular/material/list";
import {MatIconModule} from "@angular/material/icon";
import {EquipeModule} from "./equipe/equipe.module";
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE} from "@angular/material/core";
import {
  MAT_MOMENT_DATE_ADAPTER_OPTIONS,
  MAT_MOMENT_DATE_FORMATS,
  MomentDateAdapter
} from "@angular/material-moment-adapter";
import {JoueurModule} from "./joueur/joueur.module";
import {TransfertModule} from "./transfert/transfert.module";

@NgModule({
  declarations: [
    AppComponent,
    SidenavComponent,
    ConfirmDialogComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    LayoutModule,
    GraphQLModule,
    HttpClientModule,
    ChampionnatModule,
    EquipeModule,
    JoueurModule,
    TransfertModule,
    MatDialogModule,
    MatButtonModule,
    MatSidenavModule,
    MatToolbarModule,
    MatListModule,
    MatIconModule
  ],
  providers: [{provide: MAT_DATE_LOCALE, useValue: 'fr'},

    // `MomentDateAdapter` and `MAT_MOMENT_DATE_FORMATS` can be automatically provided by importing
    // `MatMomentDateModule` in your applications root module. We provide it at the component level
    // here, due to limitations of our example generation script.
    {
      provide: DateAdapter,
      useClass: MomentDateAdapter,
      deps: [MAT_DATE_LOCALE, MAT_MOMENT_DATE_ADAPTER_OPTIONS]
    },
    {provide: MAT_DATE_FORMATS, useValue: MAT_MOMENT_DATE_FORMATS},],
  bootstrap: [AppComponent]
})
export class AppModule {
}
