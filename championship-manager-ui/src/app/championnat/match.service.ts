import {Injectable} from '@angular/core';
import {ChampionnatByIdGQL, MatchDto, MatchGQL} from "../generated/graphql";

@Injectable({
  providedIn: 'root'
})
export class MatchService {

  constructor(private matchMutation: MatchGQL,
              private championnatQuery: ChampionnatByIdGQL) {
  }

  createOrUpdateMatch(match: MatchDto, championnatId: number) {
    return this.matchMutation.mutate({match: match}, {
        refetchQueries: [{
          query: this.championnatQuery.document,
          variables: {id: championnatId}
        }]
      }
    ).subscribe();
  }
}
