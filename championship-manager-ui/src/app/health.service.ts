import {Injectable} from '@angular/core';
import {UpGQL} from "./generated/graphql";
import {pluck} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class HealthService {

  constructor(private upQuery: UpGQL) {
  }

  upStatus() {
    return this.upQuery.watch().valueChanges.pipe(pluck('data', 'up'))
  }
}
