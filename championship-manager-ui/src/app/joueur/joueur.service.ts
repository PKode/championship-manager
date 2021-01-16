import {Injectable} from '@angular/core';
import {
  DeleteJoueurGQL, JoueurDto,
  JoueurDtoInput,
  JoueurGQL,
  JoueursByEquipeGQL,
  JoueursGQL,
  TransfertGQL
} from "../generated/graphql";
import {pluck} from "rxjs/operators";
import {PureQueryOptions} from "apollo-client";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class JoueurService {

  constructor(private joueurQuery: JoueursGQL,
              private joueursByEquipeQuery: JoueursByEquipeGQL,
              private joueurMutation: JoueurGQL,
              private deleteJoueurMutation: DeleteJoueurGQL,
              private transfertMutation: TransfertGQL,
              private http: HttpClient) {
  }

  getAllJoueurs() {
    return this.joueurQuery.watch().valueChanges.pipe(pluck('data', 'joueurs'))
  }

  getAllJoueursByEquipe(equipeId: number) {
    return this.joueursByEquipeQuery.watch({equipeId: equipeId}).valueChanges.pipe(pluck('data', 'joueursByEquipe'))
  }

  createOrUpdateJoueur(joueur: JoueurDtoInput) {
    let refetchQueries: PureQueryOptions[] = [{query: this.joueurQuery.document} as PureQueryOptions]
    if (joueur.equipe != null) {
      refetchQueries.push(
        {
          query: this.joueursByEquipeQuery.document,
          variables: {equipeId: joueur.equipe?.id}
        } as PureQueryOptions
      )
    }
    this.joueurMutation.mutate({joueur: joueur}, {
        refetchQueries: refetchQueries
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

  transfertJoueurs(joueurIds: number[], toEquipeId: number, fromEquipeId: number) {
    this.transfertMutation.mutate({joueurIds: joueurIds, equipeId: toEquipeId}, {
        refetchQueries: [{
          query: this.joueursByEquipeQuery.document,
          variables: {equipeId: toEquipeId}
        },
          {
            query: this.joueursByEquipeQuery.document,
            variables: {equipeId: fromEquipeId}
          }
        ]
      }
    ).subscribe();
  }

  upload(file: File) {
    let data = new FormData()
    data.append('file', file, file.name)
    return this.http.post<JoueurDto>(environment.uploadUri + '/joueur', data)
  }
}
