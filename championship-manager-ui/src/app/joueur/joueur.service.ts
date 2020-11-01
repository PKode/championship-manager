import {Injectable} from '@angular/core';
import {DeleteJoueurGQL, JoueurDtoInput, JoueurGQL, JoueursByEquipeGQL, JoueursGQL} from "../generated/graphql";
import {pluck} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class JoueurService {

  constructor(private joueurQuery: JoueursGQL,
              private joueursByEquipeQuery: JoueursByEquipeGQL,
              private joueurMutation: JoueurGQL,
              private deleteJoueurMutation: DeleteJoueurGQL) {
  }

  getAllJoueurs() {
    return this.joueurQuery.watch().valueChanges.pipe(pluck('data', 'joueurs'))
  }

  getAllJoueursByEquipe(equipeId: number) {
    return this.joueursByEquipeQuery.watch({equipeId: equipeId}).valueChanges.pipe(pluck('data', 'joueursByEquipe'))
  }

  createOrUpdateJoueur(joueur: JoueurDtoInput) {
    this.joueurMutation.mutate({joueur: joueur}, {
        refetchQueries: [{
          query: this.joueurQuery.document
        }]
      }
    ).subscribe();
  }


  deleteJoueur(id: number) {
    this.deleteJoueurMutation.mutate({id: id}, {
        refetchQueries: [{
          query: this.joueurQuery.document
        }]
      }
    ).subscribe();
  }
}
