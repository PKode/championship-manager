import {Injectable} from '@angular/core';
import {MatPaginatorIntl} from "@angular/material/paginator";

@Injectable()
export class JourneeMatPaginatorIntl extends MatPaginatorIntl {
  constructor() {
    super();

    this.getAndInitTranslations();
  }

  getAndInitTranslations() {

    this.itemsPerPageLabel = "test";
    this.nextPageLabel = "test";
    this.previousPageLabel = "test";
    this.changes.next();

  }

  getRangeLabel = (page: number, pageSize: number, length: number) => {
    if (length === 0 || pageSize === 0) {
      return `0 / ${length}`;
    }
    length = Math.max(length, 0);
    const startIndex = page * pageSize;
    const endIndex = startIndex < length ? Math.min(startIndex + pageSize, length) : startIndex + pageSize;
    return `Journée ${page + 1} / ${length}`;
  }
}
