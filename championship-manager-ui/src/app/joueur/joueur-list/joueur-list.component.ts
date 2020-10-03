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
  displayedColumns = ['id', 'nom', 'prenom', 'poste', 'equipe', 'nationalite', 'dateNaissance', 'taille', 'poids', 'actions'];

  constructor(private joueurService: JoueurService,
              private dialog: MatDialog) {
  }

  ngOnInit() {
    this.joueurService.getAllJoueurs().subscribe(data => {
      let joueurs = data.map(d => d as JoueurDto);
      this.dataSource = new JoueurListDataSource(joueurs);
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
      this.table.dataSource = this.dataSource;
    });
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
