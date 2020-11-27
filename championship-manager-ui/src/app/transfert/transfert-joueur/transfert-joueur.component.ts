import {Component, Input, OnChanges, OnInit, SimpleChanges, ViewChild} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTable} from '@angular/material/table';
import {TransfertJoueurDatasource} from './transfert-joueur-datasource';
import {JoueurDto} from "../../generated/graphql";
import {JoueurService} from "../../joueur/joueur.service";

@Component({
  selector: 'app-transfert-joueur',
  templateUrl: './transfert-joueur.component.html',
  styleUrls: ['./transfert-joueur.component.scss']
})
export class TransfertJoueurComponent implements OnInit, OnChanges {
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatTable) table: MatTable<JoueurDto>;
  dataSource: TransfertJoueurDatasource;

  /** Columns displayed in the table. Columns IDs can be added, removed, or reordered. */
  displayedColumns = ['nom', 'nationalite', 'dateNaissance', 'selected'];

  @Input()
  equipeId: number;

  selectedJoueur: JoueurDto[] = []

  allSelected: boolean = false;

  constructor(private joueurService: JoueurService) {
  }

  ngOnInit() {

  }

  ngOnChanges(changes: SimpleChanges) {
    this.equipeId = changes['equipeId'].currentValue;
    if (this.equipeId != null) {
      this.joueurService.getAllJoueursByEquipe(this.equipeId).subscribe(data => {
        let joueurs = data.map(d => d as JoueurDto);
        this.dataSource = new TransfertJoueurDatasource(joueurs);
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
        this.table.dataSource = this.dataSource;
      });
    }
  }

  selectAll(checked: boolean) {
    if (checked) {
      this.selectedJoueur = this.dataSource.data;
    } else {
      this.selectedJoueur = [];
    }

    console.log(this.selectedJoueur);
  }

  toggleSelection(checked: boolean, joueur: JoueurDto) {
    if (checked) {
      this.selectedJoueur.push(joueur);
    } else {
      this.selectedJoueur.splice(this.selectedJoueur.indexOf(joueur), 1);
    }

    this.allSelected = this.selectedJoueur.length == this.dataSource.data.length

    console.log(this.selectedJoueur);
  }

  isSelected(row: JoueurDto) {
    return this.selectedJoueur.includes(row);
  }
}
