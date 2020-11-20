import {Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTable} from '@angular/material/table';
import {EquipeMatchsDataSource} from './equipe-matchs-datasource';
import {JoueurStatDto, MatchDto} from "../../generated/graphql";
import {MatchService} from "../../championnat/match.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-equipe-matchs',
  templateUrl: './equipe-matchs.component.html',
  styleUrls: ['./equipe-matchs.component.scss']
})
export class EquipeMatchsComponent implements OnInit {
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatTable) table: MatTable<MatchDto>;
  dataSource: EquipeMatchsDataSource;

  /** Columns displayed in the table. Columns IDs can be added, removed, or reordered. */
  displayedColumns = ['date', 'domicile', 'score', 'exterieur', 'buteurs'];
  equipeId: number;

  matchs: MatchDto[] = []

  constructor(private matchService: MatchService,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.equipeId = Number.parseInt(params.get("id"));
      this.matchService.getMatchByEquipe(this.equipeId).subscribe(
        data => {
          let matchs = data.map(d => d as MatchDto);
          this.dataSource = new EquipeMatchsDataSource(matchs);
          this.dataSource.sort = this.sort;
          this.dataSource.paginator = this.paginator;
          this.table.dataSource = this.dataSource;
        });
    });
  }

  buteursDomicile(joueurs: JoueurStatDto[]) {
    return joueurs.filter(js => js.joueur.equipe.id == this.equipeId)
      .filter(js => js.nbButs > 0)
      .map(js => ' ' + js.joueur.nom + ' (' + js.nbButs + ')')
  }
}
