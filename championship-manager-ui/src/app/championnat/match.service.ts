import {Injectable} from '@angular/core';
import {
  ClassementGQL,
  ClassementJoueurGQL,
  MatchDto,
  MatchGQL,
  MatchsByEquipeAndCurrentSaisonGQL,
  SaisonGQL
} from "../generated/graphql";
import {pluck} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class MatchService {

  constructor(private matchMutation: MatchGQL,
              private matchByEquipeQuery: MatchsByEquipeAndCurrentSaisonGQL,
              private saisonQuery: SaisonGQL,
              private classementQuery: ClassementGQL,
              private classementJoueurQuery: ClassementJoueurGQL) {
  }

  createOrUpdateMatch(match: MatchDto, championnatId: number, saison: number) {
    return this.matchMutation.mutate({match: match}, {
        refetchQueries: [{
          query: this.saisonQuery.document,
          variables: {championnatId: championnatId, saison: saison}
        },
          {
            query: this.classementQuery.document,
            variables: {championnatId: championnatId, saison: saison}
          },
          {
            query: this.classementJoueurQuery.document,
            variables: {championnatId: championnatId, saison: saison}
          }
        ]
      }
    ).subscribe();
  }

  getMatchByEquipe(equipeId: number) {
    return this.matchByEquipeQuery.watch({equipeId: equipeId}).valueChanges.pipe(pluck('data', 'matchsByEquipeAndCurrentSaison'))
  }
}
