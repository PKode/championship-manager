import {Injectable} from '@angular/core';
import {
  CalendrierGQL,
  ChampionnatByIdGQL,
  ChampionnatGQL,
  ChampionnatsGQL, ClassementGQL,
  DeleteChampionnatGQL, SaisonGQL, SaisonsGQL
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
              public championnatByIdQuery: ChampionnatByIdGQL,
              private classementQuery: ClassementGQL,
              private saisonsListQuery: SaisonsGQL,
              private saisonQuery: SaisonGQL) {
  }

  getAllChampionnats() {
    return this.championnatQuery.watch().valueChanges.pipe(pluck('data', 'championnats'))
  }

  getChampionnatById(championnatId: number) {
    return this.championnatByIdQuery.watch({id: championnatId}).valueChanges.pipe(pluck('data', 'championnat'))
  }

  getSaison(championnatId: number, saison: number) {
    return this.saisonQuery.watch({championnatId: championnatId, saison: saison}).valueChanges.pipe(pluck('data', 'saison'))
  }

  createOrUpdateChampionnat(championnat: Championnat) {
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

  genererCalendrier(championnatId: number, dateDebut: string) {
    return this.calendrierMutation.mutate({championnatId: championnatId, dateDebut: dateDebut}, {
      refetchQueries: [{
        query: this.championnatByIdQuery.document,
        variables: {id: championnatId}
      }]
    })
  }

  getClassement(championnatId: number, saison: number) {
    return this.classementQuery.watch({championnatId: championnatId, saison: saison})
      .valueChanges.pipe(pluck('data', 'classement'))
  }

  getSaisons(championnatId: number) {
    return this.saisonsListQuery.watch({championnatId: championnatId})
      .valueChanges.pipe(pluck('data', 'championnat','saisons'))
  }
}
