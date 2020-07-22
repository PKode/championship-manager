import {Injectable} from '@angular/core';
import {ChampionnatByIdGQL, ClassementGQL, MatchDto, MatchGQL} from "../generated/graphql";

@Injectable({
  providedIn: 'root'
})
export class MatchService {

  constructor(private matchMutation: MatchGQL,
              private championnatQuery: ChampionnatByIdGQL,
              private classementQuery: ClassementGQL) {
  }

  createOrUpdateMatch(match: MatchDto, championnatId: number, saison: number) {
    return this.matchMutation.mutate({match: match}, {
        refetchQueries: [{
          query: this.championnatQuery.document,
          variables: {id: championnatId}
        },
          {
            query: this.classementQuery.document,
            variables: {championnatId: championnatId, saison: saison}
          }]
      }
    ).subscribe();
  }
}
