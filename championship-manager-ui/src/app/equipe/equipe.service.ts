import {Injectable} from '@angular/core';
import {pluck} from "rxjs/operators";
import {Equipe} from "./equipe";
import {DeleteEquipeGQL, EquipeGQL, EquipesGQL} from "../generated/graphql";

@Injectable({
  providedIn: 'root'
})
export class EquipeService {

  constructor(private equipeMutation: EquipeGQL,
              private equipeQuery: EquipesGQL,
              private deleteEquipeMutation: DeleteEquipeGQL) {
  }

  getAllEquipes() {
    return this.equipeQuery.watch().valueChanges.pipe(pluck('data', "equipes"))
  }

  createEquipe(equipe: Equipe) {
    this.equipeMutation.mutate({
        equipe: {
          nom: equipe.nom,
          id: equipe.id,
          championnat: equipe.championnat ? {id: equipe.championnat.id, nom: equipe.championnat.nom, saisons: []} : null
        }
      },
      {
        refetchQueries: [{
          query: this.equipeQuery.document
        }]
      }
    ).subscribe();
  }

  deleteEquipe(id: number) {
    this.deleteEquipeMutation.mutate({id: id}, {
        refetchQueries: [{
          query: this.equipeQuery.document
        }]
      }
    ).subscribe();
  }
}
