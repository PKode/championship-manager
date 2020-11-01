import {Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator, MatPaginatorIntl} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTable} from '@angular/material/table';
import {CalendrierDataSource} from './calendrier-datasource';
import {JourneeMatPaginatorIntl} from "./journee-mat-paginator";
import {ChampionnatService} from "../championnat.service";
import {MatchDto} from "../../generated/graphql";
import {ActivatedRoute} from "@angular/router";
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
  saison: number;

  constructor(private championnatService: ChampionnatService,
              private route: ActivatedRoute,
              private dialog: MatDialog) {
  }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.championnatId = Number.parseInt(params.get("id"));
      this.saison = Number.parseInt(params.get("saison"));
      if (!isNaN(this.saison)) {
        this.championnatService.getSaison(this.championnatId, this.saison).subscribe(
          data => {
            let matchs: MatchDto[];
            let matchPerDay: number;
            matchs = data.journees?.flatMap(j => j.matchs.map(m => m as MatchDto));
            matchPerDay = data.journees[0].matchs.length;
            this.updateDataSource(matchs, matchPerDay);
          });
      }
    });
  }

  updateDataSource(matchs: MatchDto[], matchPerDay: number) {
    this.dataSource = new CalendrierDataSource(matchs);
    this.dataSource.sort = this.sort;
    this.paginator.pageSize = matchPerDay;
    this.dataSource.paginator = this.paginator;
    this.table.dataSource = this.dataSource;
  }

  play(row: MatchDto) {
    const dialogRef = this.dialog.open(MatchFormComponent, {
      width: '1000px',
      maxHeight: '750px',
      data: {match: row, championnatId: this.championnatId, saison: this.saison}
    });

    dialogRef.afterClosed().subscribe(result => {
    });
  }
}
