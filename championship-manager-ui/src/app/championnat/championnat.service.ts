import {Injectable} from '@angular/core';
import {CalendrierGQL, ChampionnatGQL, ChampionnatsGQL, DeleteChampionnatGQL} from "../generated/graphql";
import {pluck} from "rxjs/operators";
import {Championnat} from "./championnat";


@Injectable({
  providedIn: 'root'
})
export class ChampionnatService {

  constructor(private championnatMutation: ChampionnatGQL,
              private championnatQuery: ChampionnatsGQL,
              private deleteChampionnatMutation: DeleteChampionnatGQL,
              private calendrierMutation: CalendrierGQL) {
  }

  getAllChampionnats() {
    return this.championnatQuery.watch().valueChanges.pipe(pluck('data', "championnats"))
  }

  createChampionnat(championnat: Championnat) {
    this.championnatMutation.mutate({championnat: {nom: championnat.nom, id: championnat.id}}, {
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

  genererCalendrier(championnatId: number) {
    this.calendrierMutation.mutate({championnatId: championnatId})
      .subscribe(value => console.log(value))
  }
}
