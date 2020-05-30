import {Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTable} from '@angular/material/table';
import {ChampionnatListDataSource} from './championnat-list-datasource';
import {ChampionnatsGQL, DeleteChampionnatGQL} from "../../generated/graphql";
import {pluck} from "rxjs/operators";
import {MatDialog} from "@angular/material/dialog";
import {ConfirmDialogComponent} from "../../confirm-dialog/confirm-dialog.component";

export class Championnat {
  id: number;
  nom: string;

  constructor(id: number, nom: string) {
    this.id = id;
    this.nom = nom;
  }
}

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

  constructor(private championnatsGQL: ChampionnatsGQL,
              private deleteChampionnatGQL: DeleteChampionnatGQL,
              private dialog: MatDialog) {
  }

  ngOnInit() {
    this.championnatsGQL.watch().valueChanges.pipe(pluck('data', "championnats"))
      .subscribe(data => {
        let map = data.map(d => new Championnat(d.id, d.nom));
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
        this.deleteChampionnatGQL.mutate({id: row.id}, {
            refetchQueries: [{
              query: this.championnatsGQL.document
            }]
          }
        ).subscribe();
      }
    });
  }
}
