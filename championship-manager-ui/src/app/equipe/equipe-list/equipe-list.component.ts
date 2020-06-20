import {Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTable} from '@angular/material/table';
import {MatDialog} from "@angular/material/dialog";
import {ConfirmDialogComponent} from "../../confirm-dialog/confirm-dialog.component";
import {Equipe} from "../equipe";
import {EquipeListDataSource} from "./equipe-list-datasource";
import {EquipeFormComponent} from "../equipe-form/equipe-form.component";
import {EquipeService} from "../equipe.service";
import {Championnat} from "../../championnat/championnat";

@Component({
  selector: 'app-equipe-list',
  templateUrl: './equipe-list.component.html',
  styleUrls: ['./equipe-list.component.scss']
})
export class EquipeListComponent implements OnInit {
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatTable) table: MatTable<Equipe>;
  dataSource: EquipeListDataSource;

  /** Columns displayed in the table. Columns IDs can be added, removed, or reordered. */
  displayedColumns = ['id', 'nom', 'actions'];

  constructor(private equipeService: EquipeService,
              private dialog: MatDialog) {
  }

  ngOnInit() {
    this.equipeService.getAllEquipes().subscribe(data => {
      let map = data.map(d => new Equipe(d.nom, new Championnat("Ligue 1"),d.id));
      this.dataSource = new EquipeListDataSource(map);
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
      this.table.dataSource = this.dataSource;
    });
  }

  delete(row: Equipe) {
    this.dialog.open(ConfirmDialogComponent,
      {
        width: '400px',
        data: 'Êtes vous sur de supprimer cette équipe ?'
      }).afterClosed().subscribe(result => {
      if (result === true) {
        this.equipeService.deleteEquipe(row.id);
      }
    });
  }

  edit(row: Equipe) {
    const dialogRef = this.dialog.open(EquipeFormComponent, {
      width: '250px',
      data: row
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }
}
