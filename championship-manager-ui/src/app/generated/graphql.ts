import gql from 'graphql-tag';
import {Injectable} from '@angular/core';
import * as Apollo from 'apollo-angular';

export type Maybe<T> = T | null;
/** All built-in and custom scalars, mapped to their actual values */
export type Scalars = {
  ID: string;
  String: string;
  Boolean: boolean;
  Int: number;
  Float: number;
};



export type ChampionnatDto = {
  readonly __typename?: 'ChampionnatDto';
  readonly id: Maybe<Scalars['Int']>;
  readonly nom: Scalars['String'];
  readonly saisons: Maybe<ReadonlyArray<SaisonDto>>;
};

export type ChampionnatDtoInput = {
  readonly id: Maybe<Scalars['Int']>;
  readonly nom: Scalars['String'];
  readonly saisons: Maybe<ReadonlyArray<SaisonDtoInput>>;
};

export type ClassementDto = {
  readonly __typename?: 'ClassementDto';
  readonly bc: Scalars['Int'];
  readonly bp: Scalars['Int'];
  readonly d: Scalars['Int'];
  readonly diff: Scalars['Int'];
  readonly equipe: EquipeDto;
  readonly mj: Scalars['Int'];
  readonly n: Scalars['Int'];
  readonly pts: Scalars['Int'];
  readonly v: Scalars['Int'];
};

export type ClassementJoueurDto = {
  readonly __typename?: 'ClassementJoueurDto';
  readonly joueur: JoueurDto;
  readonly nbButs: Scalars['Int'];
  readonly nbCartonsJaunes: Scalars['Int'];
  readonly nbCartonsRouges: Scalars['Int'];
  readonly nbMatchs: Scalars['Int'];
  readonly nbPasses: Scalars['Int'];
  readonly ratioBut: Scalars['Float'];
};

export type EquipeDto = {
  readonly __typename?: 'EquipeDto';
  readonly championnat: Maybe<ChampionnatDto>;
  readonly id: Maybe<Scalars['Int']>;
  readonly nom: Scalars['String'];
};

export type EquipeDtoInput = {
  readonly championnat: Maybe<ChampionnatDtoInput>;
  readonly id: Maybe<Scalars['Int']>;
  readonly nom: Scalars['String'];
};

export type JoueurDto = {
  readonly __typename?: 'JoueurDto';
  readonly dateNaissance: Scalars['String'];
  readonly equipe: Maybe<EquipeDto>;
  readonly id: Maybe<Scalars['Int']>;
  readonly nationalite: Scalars['String'];
  readonly nom: Scalars['String'];
  readonly poids: Scalars['Int'];
  readonly poste: Scalars['String'];
  readonly prenom: Scalars['String'];
  readonly taille: Scalars['Int'];
};

export type JoueurDtoInput = {
  readonly dateNaissance: Scalars['String'];
  readonly equipe: Maybe<EquipeDtoInput>;
  readonly id: Maybe<Scalars['Int']>;
  readonly nationalite: Scalars['String'];
  readonly nom: Scalars['String'];
  readonly poids: Scalars['Int'];
  readonly poste: Scalars['String'];
  readonly prenom: Scalars['String'];
  readonly taille: Scalars['Int'];
};

export type JoueurStatDto = {
  readonly __typename?: 'JoueurStatDto';
  readonly joueur: JoueurDto;
  readonly nbButs: Scalars['Int'];
  readonly nbCartonsJaunes: Scalars['Int'];
  readonly nbCartonsRouges: Scalars['Int'];
  readonly nbPasses: Scalars['Int'];
};

export type JoueurStatDtoInput = {
  readonly joueur: JoueurDtoInput;
  readonly nbButs: Scalars['Int'];
  readonly nbCartonsJaunes: Scalars['Int'];
  readonly nbCartonsRouges: Scalars['Int'];
  readonly nbPasses: Scalars['Int'];
};

export type JourneeDto = {
  readonly __typename?: 'JourneeDto';
  readonly matchs: ReadonlyArray<MatchDto>;
  readonly numero: Scalars['Int'];
};

export type JourneeDtoInput = {
  readonly matchs: ReadonlyArray<MatchDtoInput>;
  readonly numero: Scalars['Int'];
};

export type MatchDto = {
  readonly __typename?: 'MatchDto';
  readonly butDomicile: Maybe<Scalars['Int']>;
  readonly butExterieur: Maybe<Scalars['Int']>;
  readonly date: Scalars['String'];
  readonly domicile: EquipeDto;
  readonly exterieur: EquipeDto;
  readonly joueurs: ReadonlyArray<JoueurStatDto>;
};

export type MatchDtoInput = {
  readonly butDomicile: Maybe<Scalars['Int']>;
  readonly butExterieur: Maybe<Scalars['Int']>;
  readonly date: Scalars['String'];
  readonly domicile: EquipeDtoInput;
  readonly exterieur: EquipeDtoInput;
  readonly joueurs: ReadonlyArray<JoueurStatDtoInput>;
};

export type Mutation = {
  readonly __typename?: 'Mutation';
  readonly calendrier: SaisonDto;
  readonly championnat: ChampionnatDto;
  readonly deleteChampionnat: ChampionnatDto;
  readonly deleteEquipe: EquipeDto;
  readonly equipe: EquipeDto;
  readonly deleteJoueur: JoueurDto;
  readonly joueur: JoueurDto;
  readonly match: MatchDto;
};


export type MutationCalendrierArgs = {
  championnatId: Scalars['Int'];
  dateDebut: Scalars['String'];
};


export type MutationChampionnatArgs = {
  championnat: ChampionnatDtoInput;
};


export type MutationDeleteChampionnatArgs = {
  id: Scalars['Int'];
};


export type MutationDeleteEquipeArgs = {
  id: Scalars['Int'];
};


export type MutationEquipeArgs = {
  equipe: EquipeDtoInput;
};


export type MutationDeleteJoueurArgs = {
  id: Scalars['Int'];
};


export type MutationJoueurArgs = {
  joueur: JoueurDtoInput;
};


export type MutationMatchArgs = {
  match: MatchDtoInput;
};

export type Query = {
  readonly __typename?: 'Query';
  readonly championnat: ChampionnatDto;
  readonly championnats: ReadonlyArray<ChampionnatDto>;
  readonly classement: ReadonlyArray<ClassementDto>;
  readonly classementJoueur: ReadonlyArray<ClassementJoueurDto>;
  readonly saison: SaisonDto;
  readonly equipe: EquipeDto;
  readonly equipes: ReadonlyArray<EquipeDto>;
  readonly equipesOfChampionnat: ReadonlyArray<EquipeDto>;
  readonly joueur: JoueurDto;
  readonly joueurs: ReadonlyArray<JoueurDto>;
  readonly joueursByEquipe: ReadonlyArray<JoueurDto>;
};


export type QueryChampionnatArgs = {
  id: Scalars['Int'];
};


export type QueryClassementArgs = {
  championnatId: Scalars['Int'];
  saison: Scalars['Int'];
};


export type QueryClassementJoueurArgs = {
  championnatId: Scalars['Int'];
  saison: Scalars['Int'];
};


export type QuerySaisonArgs = {
  championnatId: Scalars['Int'];
  saison: Scalars['Int'];
};


export type QueryEquipeArgs = {
  id: Scalars['Int'];
};


export type QueryEquipesOfChampionnatArgs = {
  championnatId: Scalars['Int'];
};


export type QueryJoueurArgs = {
  id: Scalars['Int'];
};


export type QueryJoueursByEquipeArgs = {
  equipeId: Scalars['Int'];
};

export type SaisonDto = {
  readonly __typename?: 'SaisonDto';
  readonly annee: Scalars['Int'];
  readonly journees: ReadonlyArray<JourneeDto>;
  readonly matchs: ReadonlyArray<MatchDto>;
};

export type SaisonDtoInput = {
  readonly annee: Scalars['Int'];
  readonly journees: ReadonlyArray<JourneeDtoInput>;
  readonly matchs: ReadonlyArray<MatchDtoInput>;
};

export type ChampionnatMutationVariables = {
  championnat: ChampionnatDtoInput;
};


export type ChampionnatMutation = (
  { readonly __typename?: 'Mutation' }
  & { readonly championnat: (
    { readonly __typename?: 'ChampionnatDto' }
    & Pick<ChampionnatDto, 'id' | 'nom'>
  ) }
);

export type DeleteChampionnatMutationVariables = {
  id: Scalars['Int'];
};


export type DeleteChampionnatMutation = (
  { readonly __typename?: 'Mutation' }
  & { readonly deleteChampionnat: (
    { readonly __typename?: 'ChampionnatDto' }
    & Pick<ChampionnatDto, 'id' | 'nom'>
  ) }
);

export type CalendrierMutationVariables = {
  championnatId: Scalars['Int'];
  dateDebut: Scalars['String'];
};


export type CalendrierMutation = (
  { readonly __typename?: 'Mutation' }
  & { readonly calendrier: (
    { readonly __typename?: 'SaisonDto' }
    & Pick<SaisonDto, 'annee'>
    & { readonly journees: ReadonlyArray<(
      { readonly __typename?: 'JourneeDto' }
      & Pick<JourneeDto, 'numero'>
      & { readonly matchs: ReadonlyArray<(
        { readonly __typename?: 'MatchDto' }
        & Pick<MatchDto, 'butDomicile' | 'butExterieur' | 'date'>
        & { readonly domicile: (
          { readonly __typename?: 'EquipeDto' }
          & Pick<EquipeDto, 'id' | 'nom'>
        ), readonly exterieur: (
          { readonly __typename?: 'EquipeDto' }
          & Pick<EquipeDto, 'id' | 'nom'>
        ) }
      )> }
    )> }
  ) }
);

export type ChampionnatsQueryVariables = {};


export type ChampionnatsQuery = (
  { readonly __typename?: 'Query' }
  & { readonly championnats: ReadonlyArray<(
    { readonly __typename?: 'ChampionnatDto' }
    & Pick<ChampionnatDto, 'id' | 'nom'>
    & { readonly saisons: Maybe<ReadonlyArray<(
      { readonly __typename?: 'SaisonDto' }
      & Pick<SaisonDto, 'annee'>
    )>> }
  )> }
);

export type ChampionnatByIdQueryVariables = {
  id: Scalars['Int'];
};


export type ChampionnatByIdQuery = (
  { readonly __typename?: 'Query' }
  & { readonly championnat: (
    { readonly __typename?: 'ChampionnatDto' }
    & Pick<ChampionnatDto, 'id' | 'nom'>
    & { readonly saisons: Maybe<ReadonlyArray<(
      { readonly __typename?: 'SaisonDto' }
      & Pick<SaisonDto, 'annee'>
      & { readonly journees: ReadonlyArray<(
        { readonly __typename?: 'JourneeDto' }
        & { readonly matchs: ReadonlyArray<(
          { readonly __typename?: 'MatchDto' }
          & Pick<MatchDto, 'butDomicile' | 'butExterieur' | 'date'>
          & { readonly domicile: (
            { readonly __typename?: 'EquipeDto' }
            & Pick<EquipeDto, 'id' | 'nom'>
          ), readonly exterieur: (
            { readonly __typename?: 'EquipeDto' }
            & Pick<EquipeDto, 'id' | 'nom'>
          ) }
        )> }
      )> }
    )>> }
  ) }
);

export type SaisonsQueryVariables = {
  championnatId: Scalars['Int'];
};


export type SaisonsQuery = (
  { readonly __typename?: 'Query' }
  & { readonly championnat: (
    { readonly __typename?: 'ChampionnatDto' }
    & { readonly saisons: Maybe<ReadonlyArray<(
      { readonly __typename?: 'SaisonDto' }
      & Pick<SaisonDto, 'annee'>
    )>> }
  ) }
);

export type SaisonQueryVariables = {
  championnatId: Scalars['Int'];
  saison: Scalars['Int'];
};


export type SaisonQuery = (
  { readonly __typename?: 'Query' }
  & { readonly saison: (
    { readonly __typename?: 'SaisonDto' }
    & Pick<SaisonDto, 'annee'>
    & { readonly journees: ReadonlyArray<(
      { readonly __typename?: 'JourneeDto' }
      & { readonly matchs: ReadonlyArray<(
        { readonly __typename?: 'MatchDto' }
        & Pick<MatchDto, 'butDomicile' | 'butExterieur' | 'date'>
        & { readonly joueurs: ReadonlyArray<(
          { readonly __typename?: 'JoueurStatDto' }
          & Pick<JoueurStatDto, 'nbButs' | 'nbPasses' | 'nbCartonsJaunes' | 'nbCartonsRouges'>
          & { readonly joueur: (
            { readonly __typename?: 'JoueurDto' }
            & JoueurWithoutEquipeFragment
          ) }
        )>, readonly domicile: (
          { readonly __typename?: 'EquipeDto' }
          & Pick<EquipeDto, 'id' | 'nom'>
        ), readonly exterieur: (
          { readonly __typename?: 'EquipeDto' }
          & Pick<EquipeDto, 'id' | 'nom'>
        ) }
      )> }
    )> }
  ) }
);

export type ClassementQueryVariables = {
  championnatId: Scalars['Int'];
  saison: Scalars['Int'];
};


export type ClassementQuery = (
  { readonly __typename?: 'Query' }
  & { readonly classement: ReadonlyArray<(
    { readonly __typename?: 'ClassementDto' }
    & Pick<ClassementDto, 'v' | 'n' | 'd' | 'bp' | 'bc' | 'mj' | 'pts' | 'diff'>
    & { readonly equipe: (
      { readonly __typename?: 'EquipeDto' }
      & Pick<EquipeDto, 'nom'>
    ) }
  )> }
);

export type EquipeMutationVariables = {
  equipe: EquipeDtoInput;
};


export type EquipeMutation = (
  { readonly __typename?: 'Mutation' }
  & { readonly equipe: (
    { readonly __typename?: 'EquipeDto' }
    & Pick<EquipeDto, 'id' | 'nom'>
  ) }
);

export type DeleteEquipeMutationVariables = {
  id: Scalars['Int'];
};


export type DeleteEquipeMutation = (
  { readonly __typename?: 'Mutation' }
  & { readonly deleteEquipe: (
    { readonly __typename?: 'EquipeDto' }
    & Pick<EquipeDto, 'id' | 'nom'>
  ) }
);

export type EquipesQueryVariables = {};


export type EquipesQuery = (
  { readonly __typename?: 'Query' }
  & { readonly equipes: ReadonlyArray<(
    { readonly __typename?: 'EquipeDto' }
    & Pick<EquipeDto, 'id' | 'nom'>
    & { readonly championnat: Maybe<(
      { readonly __typename?: 'ChampionnatDto' }
      & Pick<ChampionnatDto, 'id' | 'nom'>
    )> }
  )> }
);

export type EquipesOfChampionnatQueryVariables = {
  championnatId: Scalars['Int'];
};


export type EquipesOfChampionnatQuery = (
  { readonly __typename?: 'Query' }
  & { readonly equipesOfChampionnat: ReadonlyArray<(
    { readonly __typename?: 'EquipeDto' }
    & Pick<EquipeDto, 'id' | 'nom'>
  )> }
);

export type JoueurWithoutEquipeFragment = (
  { readonly __typename?: 'JoueurDto' }
  & Pick<JoueurDto, 'id' | 'nom' | 'prenom' | 'poste' | 'nationalite' | 'dateNaissance' | 'taille' | 'poids'>
);

export type JoueurMutationVariables = {
  joueur: JoueurDtoInput;
};


export type JoueurMutation = (
  { readonly __typename?: 'Mutation' }
  & { readonly joueur: (
    { readonly __typename?: 'JoueurDto' }
    & Pick<JoueurDto, 'id' | 'nom'>
  ) }
);

export type DeleteJoueurMutationVariables = {
  id: Scalars['Int'];
};


export type DeleteJoueurMutation = (
  { readonly __typename?: 'Mutation' }
  & { readonly deleteJoueur: (
    { readonly __typename?: 'JoueurDto' }
    & Pick<JoueurDto, 'id' | 'nom'>
  ) }
);

export type JoueursQueryVariables = {};


export type JoueursQuery = (
  { readonly __typename?: 'Query' }
  & { readonly joueurs: ReadonlyArray<(
    { readonly __typename?: 'JoueurDto' }
    & Pick<JoueurDto, 'id' | 'nom' | 'prenom' | 'poste' | 'nationalite' | 'dateNaissance' | 'taille' | 'poids'>
    & { readonly equipe: Maybe<(
      { readonly __typename?: 'EquipeDto' }
      & Pick<EquipeDto, 'id' | 'nom'>
    )> }
  )> }
);

export type JoueursByEquipeQueryVariables = {
  equipeId: Scalars['Int'];
};


export type JoueursByEquipeQuery = (
  { readonly __typename?: 'Query' }
  & { readonly joueursByEquipe: ReadonlyArray<(
    { readonly __typename?: 'JoueurDto' }
    & JoueurWithoutEquipeFragment
  )> }
);

export type MatchMutationVariables = {
  match: MatchDtoInput;
};


export type MatchMutation = (
  { readonly __typename?: 'Mutation' }
  & { readonly match: (
    { readonly __typename?: 'MatchDto' }
    & Pick<MatchDto, 'date' | 'butDomicile' | 'butExterieur'>
    & { readonly domicile: (
      { readonly __typename?: 'EquipeDto' }
      & Pick<EquipeDto, 'nom'>
    ), readonly exterieur: (
      { readonly __typename?: 'EquipeDto' }
      & Pick<EquipeDto, 'nom'>
    ) }
  ) }
);

export const JoueurWithoutEquipeFragmentDoc = gql`
    fragment JoueurWithoutEquipe on JoueurDto {
  id
  nom
  prenom
  poste
  nationalite
  dateNaissance
  taille
  poids
}
    `;
export const ChampionnatDocument = gql`
    mutation championnat($championnat: ChampionnatDtoInput!) {
  championnat(championnat: $championnat) {
    id
    nom
  }
}
    `;

  @Injectable({
    providedIn: 'root'
  })
  export class ChampionnatGQL extends Apollo.Mutation<ChampionnatMutation, ChampionnatMutationVariables> {
    document = ChampionnatDocument;

  }
export const DeleteChampionnatDocument = gql`
    mutation deleteChampionnat($id: Int!) {
  deleteChampionnat(id: $id) {
    id
    nom
  }
}
    `;

  @Injectable({
    providedIn: 'root'
  })
  export class DeleteChampionnatGQL extends Apollo.Mutation<DeleteChampionnatMutation, DeleteChampionnatMutationVariables> {
    document = DeleteChampionnatDocument;

  }
export const CalendrierDocument = gql`
    mutation calendrier($championnatId: Int!, $dateDebut: String!) {
  calendrier(championnatId: $championnatId, dateDebut: $dateDebut) {
    annee
    journees {
      numero
      matchs {
        domicile {
          id
          nom
        }
        exterieur {
          id
          nom
        }
        butDomicile
        butExterieur
        date
      }
    }
  }
}
    `;

  @Injectable({
    providedIn: 'root'
  })
  export class CalendrierGQL extends Apollo.Mutation<CalendrierMutation, CalendrierMutationVariables> {
    document = CalendrierDocument;

  }
export const ChampionnatsDocument = gql`
    query championnats {
  championnats {
    id
    nom
    saisons {
      annee
    }
  }
}
    `;

  @Injectable({
    providedIn: 'root'
  })
  export class ChampionnatsGQL extends Apollo.Query<ChampionnatsQuery, ChampionnatsQueryVariables> {
    document = ChampionnatsDocument;

  }
export const ChampionnatByIdDocument = gql`
    query championnatById($id: Int!) {
  championnat(id: $id) {
    id
    nom
    saisons {
      annee
      journees {
        matchs {
          butDomicile
          butExterieur
          date
          domicile {
            id
            nom
          }
          exterieur {
            id
            nom
          }
        }
      }
    }
  }
}
    `;

  @Injectable({
    providedIn: 'root'
  })
  export class ChampionnatByIdGQL extends Apollo.Query<ChampionnatByIdQuery, ChampionnatByIdQueryVariables> {
    document = ChampionnatByIdDocument;

  }
export const SaisonsDocument = gql`
    query saisons($championnatId: Int!) {
  championnat(id: $championnatId) {
    saisons {
      annee
    }
  }
}
    `;

  @Injectable({
    providedIn: 'root'
  })
  export class SaisonsGQL extends Apollo.Query<SaisonsQuery, SaisonsQueryVariables> {
    document = SaisonsDocument;

  }
export const SaisonDocument = gql`
    query saison($championnatId: Int!, $saison: Int!) {
  saison(championnatId: $championnatId, saison: $saison) {
    annee
    journees {
      matchs {
        butDomicile
        butExterieur
        date
        joueurs {
          joueur {
            ...JoueurWithoutEquipe
          }
          nbButs
          nbPasses
          nbCartonsJaunes
          nbCartonsRouges
        }
        domicile {
          id
          nom
        }
        exterieur {
          id
          nom
        }
      }
    }
  }
}
    ${JoueurWithoutEquipeFragmentDoc}`;

  @Injectable({
    providedIn: 'root'
  })
  export class SaisonGQL extends Apollo.Query<SaisonQuery, SaisonQueryVariables> {
    document = SaisonDocument;

  }
export const ClassementDocument = gql`
    query classement($championnatId: Int!, $saison: Int!) {
  classement(championnatId: $championnatId, saison: $saison) {
    equipe {
      nom
    }
    v
    n
    d
    bp
    bc
    mj
    pts
    diff
  }
}
    `;

  @Injectable({
    providedIn: 'root'
  })
  export class ClassementGQL extends Apollo.Query<ClassementQuery, ClassementQueryVariables> {
    document = ClassementDocument;

  }
export const EquipeDocument = gql`
    mutation equipe($equipe: EquipeDtoInput!) {
  equipe(equipe: $equipe) {
    id
    nom
  }
}
    `;

  @Injectable({
    providedIn: 'root'
  })
  export class EquipeGQL extends Apollo.Mutation<EquipeMutation, EquipeMutationVariables> {
    document = EquipeDocument;

  }
export const DeleteEquipeDocument = gql`
    mutation deleteEquipe($id: Int!) {
  deleteEquipe(id: $id) {
    id
    nom
  }
}
    `;

  @Injectable({
    providedIn: 'root'
  })
  export class DeleteEquipeGQL extends Apollo.Mutation<DeleteEquipeMutation, DeleteEquipeMutationVariables> {
    document = DeleteEquipeDocument;

  }
export const EquipesDocument = gql`
    query equipes {
  equipes {
    id
    nom
    championnat {
      id
      nom
    }
  }
}
    `;

  @Injectable({
    providedIn: 'root'
  })
  export class EquipesGQL extends Apollo.Query<EquipesQuery, EquipesQueryVariables> {
    document = EquipesDocument;

  }
export const EquipesOfChampionnatDocument = gql`
    query equipesOfChampionnat($championnatId: Int!) {
  equipesOfChampionnat(championnatId: $championnatId) {
    id
    nom
  }
}
    `;

  @Injectable({
    providedIn: 'root'
  })
  export class EquipesOfChampionnatGQL extends Apollo.Query<EquipesOfChampionnatQuery, EquipesOfChampionnatQueryVariables> {
    document = EquipesOfChampionnatDocument;

  }
export const JoueurDocument = gql`
    mutation joueur($joueur: JoueurDtoInput!) {
  joueur(joueur: $joueur) {
    id
    nom
  }
}
    `;

  @Injectable({
    providedIn: 'root'
  })
  export class JoueurGQL extends Apollo.Mutation<JoueurMutation, JoueurMutationVariables> {
    document = JoueurDocument;

  }
export const DeleteJoueurDocument = gql`
    mutation deleteJoueur($id: Int!) {
  deleteJoueur(id: $id) {
    id
    nom
  }
}
    `;

  @Injectable({
    providedIn: 'root'
  })
  export class DeleteJoueurGQL extends Apollo.Mutation<DeleteJoueurMutation, DeleteJoueurMutationVariables> {
    document = DeleteJoueurDocument;

  }
export const JoueursDocument = gql`
    query joueurs {
  joueurs {
    id
    nom
    prenom
    poste
    nationalite
    dateNaissance
    taille
    poids
    equipe {
      id
      nom
    }
  }
}
    `;

  @Injectable({
    providedIn: 'root'
  })
  export class JoueursGQL extends Apollo.Query<JoueursQuery, JoueursQueryVariables> {
    document = JoueursDocument;

  }
export const JoueursByEquipeDocument = gql`
    query joueursByEquipe($equipeId: Int!) {
  joueursByEquipe(equipeId: $equipeId) {
    ...JoueurWithoutEquipe
  }
}
    ${JoueurWithoutEquipeFragmentDoc}`;

  @Injectable({
    providedIn: 'root'
  })
  export class JoueursByEquipeGQL extends Apollo.Query<JoueursByEquipeQuery, JoueursByEquipeQueryVariables> {
    document = JoueursByEquipeDocument;

  }
export const MatchDocument = gql`
    mutation match($match: MatchDtoInput!) {
  match(match: $match) {
    date
    domicile {
      nom
    }
    exterieur {
      nom
    }
    butDomicile
    butExterieur
  }
}
    `;

  @Injectable({
    providedIn: 'root'
  })
  export class MatchGQL extends Apollo.Mutation<MatchMutation, MatchMutationVariables> {
    document = MatchDocument;

  }
