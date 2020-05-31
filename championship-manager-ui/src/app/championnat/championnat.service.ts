import { Injectable } from '@angular/core';
import {ChampionnatGQL, ChampionnatsGQL, DeleteChampionnatGQL} from "../generated/graphql";
import {pluck} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class ChampionnatService {

  constructor(private championnatMutation: ChampionnatGQL,
              private championnatQuery: ChampionnatsGQL,
              private deleteChampionnatMutation: DeleteChampionnatGQL) { }

  getAllChampionnats() {
    return this.championnatQuery.watch().valueChanges.pipe(pluck('data', "championnats"))
  }
   createChampionnat(nom: string) {
     this.championnatMutation.mutate({nom: nom}, {
         refetchQueries: [{
           query: this.championnatQuery.document
         }]
       }
     ).subscribe();
   }

   deleteChampionnat(id: number) {
     this.deleteChampionnatMutation.mutate({id: id}, {
         refetchQueries: [{
           query: this.championnatQuery.document
         }]
       }
     ).subscribe();
   }
}
