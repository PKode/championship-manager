import {Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator, MatPaginatorIntl} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTable} from '@angular/material/table';
import {CalendrierDataSource} from './calendrier-datasource';
import {JourneeMatPaginatorIntl} from "./journee-mat-paginator";
import {ChampionnatService} from "../championnat.service";
import {MatchDto} from "../../generated/graphql";
import {ActivatedRoute} from "@angular/router";

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
  displayedColumns = ['date', 'domicile', 'score', 'exterieur'];

  constructor(private championnatService: ChampionnatService,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.championnatService.getChampionnatById(Number.parseInt(params.get("id"))).subscribe(
        data => {
          // @ts-ignore
          let journees = data.saisons[data.saisons.length - 1].journees;
          let matchs = journees.flatMap(j => j.matchs.map(m => m as MatchDto));
          this.dataSource = new CalendrierDataSource(matchs);
          this.dataSource.sort = this.sort;
          this.paginator.pageSize = journees[0].matchs.length;
          this.dataSource.paginator = this.paginator;
          this.table.dataSource = this.dataSource;
        });
    });
  }
}
