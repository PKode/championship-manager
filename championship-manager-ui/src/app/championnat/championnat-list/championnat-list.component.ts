import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTable } from '@angular/material/table';
import { ChampionnatListDataSource, ChampionnatListItem } from './championnat-list-datasource';

@Component({
  selector: 'app-championnat-list',
  templateUrl: './championnat-list.component.html',
  styleUrls: ['./championnat-list.component.scss']
})
export class ChampionnatListComponent implements AfterViewInit, OnInit {
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatTable) table: MatTable<ChampionnatListItem>;
  dataSource: ChampionnatListDataSource;

  /** Columns displayed in the table. Columns IDs can be added, removed, or reordered. */
  displayedColumns = ['id', 'nom'];

  ngOnInit() {
    this.dataSource = new ChampionnatListDataSource();
  }

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
    this.table.dataSource = this.dataSource;
  }
}
