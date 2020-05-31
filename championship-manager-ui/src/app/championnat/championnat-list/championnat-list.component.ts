import {Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTable} from '@angular/material/table';
import {ChampionnatListDataSource} from './championnat-list-datasource';
import {MatDialog} from "@angular/material/dialog";
import {ConfirmDialogComponent} from "../../confirm-dialog/confirm-dialog.component";
import {ChampionnatService} from "../championnat.service";
import {Championnat} from "../championnat";
import {ChampionnatFormComponent} from "../championnat-form/championnat-form.component";

@Component({
  selector: 'app-championnat-list',
  templateUrl: './championnat-list.component.html',
  styleUrls: ['./championnat-list.component.scss']
})
export class ChampionnatListComponent implements OnInit {
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatTable) table: MatTable<Championnat>;
  dataSource: ChampionnatListDataSource;

  /** Columns displayed in the table. Columns IDs can be added, removed, or reordered. */
  displayedColumns = ['id', 'nom', 'actions'];

  constructor(private championnatService: ChampionnatService,
              private dialog: MatDialog) {
  }

  ngOnInit() {
    this.championnatService.getAllChampionnats().subscribe(data => {
      let map = data.map(d => new Championnat(d.nom, d.id));
      this.dataSource = new ChampionnatListDataSource(map);
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
      this.table.dataSource = this.dataSource;
    });
  }

  delete(row: Championnat) {
    this.dialog.open(ConfirmDialogComponent,
      {
        width: '400px',
        data: 'Êtes vous sur de supprimer ce championnat ?'
      }).afterClosed().subscribe(result => {
      if (result === true) {
        this.championnatService.deleteChampionnat(row.id);
      }
    });
  }

  edit(row: Championnat) {
    const dialogRef = this.dialog.open(ChampionnatFormComponent, {
      width: '250px',
      data: row
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }
}
