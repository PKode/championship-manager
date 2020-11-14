import {Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTable} from '@angular/material/table';
import {ClassementButeurDataSource} from './classement-buteur-datasource';
import {ChampionnatService} from "../championnat.service";
import {ClassementJoueurDto} from "../../generated/graphql";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-classement-buteur',
  templateUrl: './classement-buteur.component.html',
  styleUrls: ['./classement-buteur.component.scss']
})
export class ClassementButeurComponent implements OnInit {
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatTable) table: MatTable<ClassementJoueurDto>;
  dataSource: ClassementButeurDataSource;

  /** Columns displayed in the table. Columns IDs can be added, removed, or reordered. */
  displayedColumns = ['place', 'joueur', 'nbMatchs', 'buts', 'passes', 'cartonsJaunes', 'cartonsRouges'];

  displayedColumnsByClassement = {
    'Buteurs': {
      displayedColumns: ['place', 'joueur', 'buts', 'nbMatchs', 'ratioButs'],
      sortedColumn: 'buts'
    },
    'Passeurs': {
      displayedColumns: ['place', 'joueur', 'passes', 'nbMatchs', 'ratioPasses'],
      sortedColumn: 'passes'
    },
    'Fairplay': {
      displayedColumns: ['place', 'joueur', 'cartonsJaunes', 'cartonsRouges', 'nbMatchs'],
      sortedColumn: 'cartonsJaunes'
    },
    'Global': {
      displayedColumns: ['place', 'joueur', 'nbMatchs', 'buts', 'passes', 'cartonsJaunes', 'cartonsRouges'],
      sortedColumn: 'buts',
    }
  }
  classements = ['Buteurs', 'Passeurs', 'Fairplay', 'Global']
  selectedClassement: string = 'Buteurs'

  constructor(private championnatService: ChampionnatService,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const championnatId = Number.parseInt(params.get("id"));
      const saison = Number.parseInt(params.get("saison"));
      if (!isNaN(saison)) {
        this.championnatService.getClassementJoueur(championnatId, saison)
          .subscribe(data => {
            this.dataSource = new ClassementButeurDataSource(data.map(it => it as ClassementJoueurDto));
            this.dataSource.sort = this.sort;
            this.dataSource.paginator = this.paginator;
            this.table.dataSource = this.dataSource;
          });
      }
    });

    this.displayedColumns = this.displayedColumnsByClassement[this.selectedClassement].displayedColumns;
  }

  changeClassement(classement: string) {
    let classementConf = this.displayedColumnsByClassement[classement];
    this.displayedColumns = classementConf.displayedColumns;
    this.dataSource.sort.sort({id: null, start: 'desc', disableClear: true})
    this.dataSource.sort.sort({id: classementConf.sortedColumn, start: 'desc', disableClear: true})
    this.selectedClassement = classement;
  }
}
