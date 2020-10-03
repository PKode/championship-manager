import {Injectable} from '@angular/core';
import {DeleteJoueurGQL, JoueurDtoInput, JoueurGQL, JoueursGQL} from "../generated/graphql";
import {pluck} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class JoueurService {

  constructor(private joueurQuery: JoueursGQL,
              private joueurMutation: JoueurGQL,
              private deleteJoueurMutation: DeleteJoueurGQL) {
  }

  getAllJoueurs() {
    return this.joueurQuery.watch().valueChanges.pipe(pluck('data', 'joueurs'))
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
