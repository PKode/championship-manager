import {Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator, MatPaginatorIntl} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTable} from '@angular/material/table';
import {CalendrierDataSource} from './calendrier-datasource';
import {JourneeMatPaginatorIntl} from "./journee-mat-paginator";
import {ChampionnatService} from "../championnat.service";
import {MatchDto, SaisonDto} from "../../generated/graphql";
import {ActivatedRoute} from "@angular/router";
import {FormControl} from "@angular/forms";
import * as moment from 'moment';
import {Championnat} from "../championnat";
import {ChampionnatFormComponent} from "../championnat-form/championnat-form.component";
import {MatDialog} from "@angular/material/dialog";
import {MatchFormComponent} from "./match-form/match-form.component";

@Component({
  selector: 'app-calendrier',
  templateUrl: './calendrier.component.html',
  styleUrls: ['./calendrier.component.scss'],
  providers: [{
    provide: MatPaginatorIntl,
    useClass: JourneeMatPaginatorIntl
  }]
})
export class CalendrierComponent implements OnInit {
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatTable) table: MatTable<MatchDto>;
  dataSource: CalendrierDataSource;

  /** Columns displayed in the table. Columns IDs can be added, removed, or reordered. */
  displayedColumns = ['date', 'domicile', 'score', 'exterieur', 'jouer'];

  championnatId: number;
  saisons: SaisonDto[];
  dateDebutNewSaison = new FormControl(moment());
  saisonFilter: number;

  constructor(private championnatService: ChampionnatService,
              private route: ActivatedRoute,
              private dialog: MatDialog) {
  }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.championnatId = Number.parseInt(params.get("id"));
      this.saisonFilter = Number.parseInt(params.get("saison"));
      this.championnatService.getChampionnatById(this.championnatId).subscribe(
        data => {
          let matchs: MatchDto[];
          let matchPerDay = 0;
          if (data.saisons.length == 0) {
            matchs = [];
          } else {
            this.saisons = data.saisons.map(s => s as SaisonDto);
            // @ts-ignore
            matchs = this.filterBySaison();
            matchPerDay = this.matchsPerDay();
          }
          this.updateDataSource(matchs, matchPerDay);
        });
    });
  }

  genererCalendrier() {
    this.championnatService.genererCalendrier(this.championnatId, this.dateDebutNewSaison.value.format('MM/DD/YYYY'))
  }

  updateDataSource(matchs: MatchDto[], matchPerDay: number) {
    this.dataSource = new CalendrierDataSource(matchs);
    this.dataSource.sort = this.sort;
    this.paginator.pageSize = matchPerDay;
    this.dataSource.paginator = this.paginator;
    this.table.dataSource = this.dataSource;
  }

  indexOfSaisonSelected() {
    return this.saisons.findIndex(value => value.annee == this.saisonFilter);
  }

  matchsPerDay() {
    let journees = this.saisons[this.indexOfSaisonSelected()].journees;
    return journees[0].matchs.length;
  }

  filterBySaison() {
    let journees = this.saisons[this.indexOfSaisonSelected()].journees;
    // @ts-ignore
    return journees?.flatMap(j => j.matchs.map(m => m as MatchDto));
  }

  play(row: MatchDto) {
      const dialogRef = this.dialog.open(MatchFormComponent, {
        width: '500px',
        data: row
      });

      dialogRef.afterClosed().subscribe(result => {
        console.log('The dialog was closed');
      });
    }
}
