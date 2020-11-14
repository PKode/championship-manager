import {DataSource} from '@angular/cdk/collections';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {map} from 'rxjs/operators';
import {merge, Observable, of as observableOf} from 'rxjs';
import {ClassementJoueurDto} from "../../generated/graphql";

/**
 * Data source for the ClassementButeur view. This class should
 * encapsulate all logic for fetching and manipulating the displayed data
 * (including sorting, pagination, and filtering).
 */
export class ClassementButeurDataSource extends DataSource<ClassementJoueurDto> {
  data: ClassementJoueurDto[];
  paginator: MatPaginator;
  sort: MatSort;

  constructor(private items: ClassementJoueurDto[]) {
    super();
    this.data = items;
  }

  /**
   * Connect this data source to the table. The table will only update when
   * the returned stream emits new items.
   * @returns A stream of the items to be rendered.
   */
  connect(): Observable<ClassementJoueurDto[]> {
    // Combine everything that affects the rendered data into one update
    // stream for the data-table to consume.
    const dataMutations = [
      observableOf(this.data),
      this.paginator.page,
      this.sort.sortChange
    ];

    return merge(...dataMutations).pipe(map(() => {
      return this.getPagedData(this.getSortedData([...this.data]));
    }));
  }

  /**
   *  Called when the table is being destroyed. Use this function, to clean up
   * any open connections or free any held resources that were set up during connect.
   */
  disconnect() {
  }

  /**
   * Paginate the data (client-side). If you're using server-side pagination,
   * this would be replaced by requesting the appropriate data from the server.
   */
  private getPagedData(data: ClassementJoueurDto[]) {
    const startIndex = this.paginator.pageIndex * this.paginator.pageSize;
    return data.splice(startIndex, this.paginator.pageSize);
  }

  /**
   * Sort the data (client-side). If you're using server-side sorting,
   * this would be replaced by requesting the appropriate data from the server.
   */
  private getSortedData(data: ClassementJoueurDto[]) {
    if (!this.sort.active || this.sort.direction === '') {
      return data;
    }

    return data.sort((a, b) => {
      const isAsc = this.sort.direction === 'asc';
      switch (this.sort.active) {
        case 'joueur':
          return compare(a.joueur.nom, b.joueur.nom, isAsc);
        case 'buts':
          return compare(+a.nbButs, +b.nbButs, isAsc);
        case 'passes':
          return compare(+a.nbPasses, +b.nbPasses, isAsc);
        case 'cartonsJaunes':
          return compare(+a.nbCartonsJaunes, +b.nbCartonsJaunes, isAsc);
        case 'cartonsRouges':
          return compare(+a.nbCartonsRouges, +b.nbCartonsRouges, isAsc);
        case 'nbMatchs':
          return compare(+a.nbMatchs, +b.nbMatchs, isAsc);
        default:
          return 0;
      }
    });
  }
}

/** Simple sort comparator for example ID/Name columns (for client-side sorting). */
function compare(a: string | number, b: string | number, isAsc: boolean) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}
