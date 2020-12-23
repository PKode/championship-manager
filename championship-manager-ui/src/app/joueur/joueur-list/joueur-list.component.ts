import {Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTable} from '@angular/material/table';
import {JoueurListDataSource} from './joueur-list-datasource';
import {JoueurDto} from "../../generated/graphql";
import {JoueurService} from "../joueur.service";
import {ConfirmDialogComponent} from "../../confirm-dialog/confirm-dialog.component";
import {JoueurFormComponent} from "../joueur-form/joueur-form.component";
import {MatDialog} from "@angular/material/dialog";
import {ActivatedRoute} from "@angular/router";
import {Observable} from "rxjs";
import {map} from "rxjs/operators";

@Component({
  selector: 'app-joueur-list',
  templateUrl: './joueur-list.component.html',
  styleUrls: ['./joueur-list.component.scss']
})
export class JoueurListComponent implements OnInit {
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatTable) table: MatTable<JoueurDto>;
  dataSource: JoueurListDataSource;

  /** Columns displayed in the table. Columns IDs can be added, removed, or reordered. */
  displayedColumns = ['nom', 'poste', 'equipe', 'nationalite', 'dateNaissance', 'taille', 'poids', 'actions'];
  displayedColumnsForEquipe = ['nom', 'poste', 'nationalite', 'dateNaissance', 'actions'];

  constructor(private joueurService: JoueurService,
              private dialog: MatDialog,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    const id: Observable<string> = this.route.params.pipe(map(p => p.id));
    const url: Observable<string> = this.route.url.pipe(map(segments => segments.join('')));
    url.subscribe(url => {
      console.log(url);
      if (url.startsWith("equipe")) {
        this.displayedColumns = this.displayedColumns.filter(c => c != 'equipe' && c != 'id');
        id.subscribe(equipeId => {
          this.joueurService.getAllJoueursByEquipe(Number.parseInt(equipeId)).subscribe(data => {
            this.initDataSource(data);
            this.displayedColumns = this.displayedColumnsForEquipe;
          });
        });
      } else {
        this.joueurService.getAllJoueurs().subscribe(data => {
          this.initDataSource(data);
        });
      }
    });
  }

  private initDataSource(data: any) {
    let joueurs = data.map(d => d as JoueurDto);
    this.dataSource = new JoueurListDataSource(joueurs);
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
    this.table.dataSource = this.dataSource;
  }

  delete(row: JoueurDto) {
    this.dialog.open(ConfirmDialogComponent,
      {
        width: '400px',
        data: 'Êtes vous sur de supprimer ce joueur ?'
      }).afterClosed().subscribe(result => {
      if (result === true) {
        this.joueurService.deleteJoueur(row.id);
      }
    });
  }

  edit(row: JoueurDto) {
    const dialogRef = this.dialog.open(JoueurFormComponent, {
      width: '500px',
      data: row
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }
}
