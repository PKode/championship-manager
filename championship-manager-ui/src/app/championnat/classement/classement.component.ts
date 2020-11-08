import {Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTable} from '@angular/material/table';
import {ClassementDataSource} from './classement-datasource';
import {ClassementDto} from "../../generated/graphql";
import {ChampionnatService} from "../championnat.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-classement',
  templateUrl: './classement.component.html',
  styleUrls: ['./classement.component.scss']
})
export class ClassementComponent implements OnInit {
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatTable) table: MatTable<ClassementDto>;
  dataSource: ClassementDataSource;

  /** Columns displayed in the table. Columns IDs can be added, removed, or reordered. */
  displayedColumns = ['place', 'equipe', 'pts', 'J.', 'G.', 'N.', 'P.', 'p.', 'c.', 'diff.'];

  constructor(private championnatService: ChampionnatService,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const championnatId = Number.parseInt(params.get("id"));
      const saison = Number.parseInt(params.get("saison"));
      if (!isNaN(saison)) {
        this.championnatService.getClassement(championnatId, saison)
          .subscribe(data => {
            this.dataSource = new ClassementDataSource(data.map(it => it as ClassementDto));
            this.dataSource.sort = this.sort;
            this.dataSource.paginator = this.paginator;
            this.table.dataSource = this.dataSource;
          });
      }
    });
  }
}
