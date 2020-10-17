import {Injectable} from '@angular/core';
import {ClassementGQL, MatchDto, MatchGQL, SaisonGQL} from "../generated/graphql";

@Injectable({
  providedIn: 'root'
})
export class MatchService {

  constructor(private matchMutation: MatchGQL,
              private saisonQuery: SaisonGQL,
              private classementQuery: ClassementGQL) {
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
          }]
      }
    ).subscribe();
  }
}
