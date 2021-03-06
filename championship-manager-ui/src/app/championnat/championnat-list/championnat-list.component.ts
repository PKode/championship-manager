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
import {Router} from "@angular/router";
import {ChampionnatDto} from "../../generated/graphql";

@Component({
  selector: 'app-championnat-list',
  templateUrl: './championnat-list.component.html',
  styleUrls: ['./championnat-list.component.scss']
})
export class ChampionnatListComponent implements OnInit {
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatTable) table: MatTable<ChampionnatDto>;
  dataSource: ChampionnatListDataSource;

  /** Columns displayed in the table. Columns IDs can be added, removed, or reordered. */
  displayedColumns = ['nom', 'actions'];

  constructor(private championnatService: ChampionnatService,
              private dialog: MatDialog,
              public router: Router) {
  }

  ngOnInit() {
    this.championnatService.getAllChampionnats().subscribe(data => {
      let championnats = data.map(d => d as ChampionnatDto);
      this.dataSource = new ChampionnatListDataSource(championnats);
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
      this.table.dataSource = this.dataSource;
    });
  }

  delete(row: ChampionnatDto) {
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
      width: '500px',
      data: row
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  goToChampionnatDetail(row: ChampionnatDto) {
    if (row.saisons.length > 0) {
      this.router.navigate(['/championnat/' + row.id + '/saison/' + row.saisons[row.saisons.length - 1]?.annee])
    } else {
      this.router.navigate(['/championnat/' + row.id])
    }
  }
}
