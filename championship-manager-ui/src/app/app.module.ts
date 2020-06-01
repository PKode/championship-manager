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
    MatDialogModule,
    MatButtonModule,
    MatSidenavModule,
    MatToolbarModule,
    MatListModule,
    MatIconModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
