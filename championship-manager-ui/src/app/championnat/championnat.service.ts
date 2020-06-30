import {Injectable} from '@angular/core';
import {
  CalendrierGQL,
  ChampionnatByIdGQL,
  ChampionnatGQL,
  ChampionnatsGQL,
  DeleteChampionnatGQL
} from "../generated/graphql";
import {pluck} from "rxjs/operators";
import {Championnat} from "./championnat";


@Injectable({
  providedIn: 'root'
})
export class ChampionnatService {

  constructor(private championnatMutation: ChampionnatGQL,
              private championnatQuery: ChampionnatsGQL,
              private deleteChampionnatMutation: DeleteChampionnatGQL,
              private calendrierMutation: CalendrierGQL,
              private championnatByIdQuery: ChampionnatByIdGQL) {
  }

  getAllChampionnats() {
    return this.championnatQuery.watch().valueChanges.pipe(pluck('data', 'championnats'))
  }

  getChampionnatById(championnatId: number) {
    return this.championnatByIdQuery.watch({id: championnatId}).valueChanges.pipe(pluck('data', 'championnat'))
  }

  createChampionnat(championnat: Championnat) {
    this.championnatMutation.mutate({championnat: {nom: championnat.nom, id: championnat.id, saisons: []}}, {
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
    this.calendrierMutation.mutate({championnatId: championnatId}, {
      refetchQueries: [{
        query: this.championnatByIdQuery.document,
        variables : {id: championnatId}
      }]
    }).subscribe(value => console.log(value))
  }
}
