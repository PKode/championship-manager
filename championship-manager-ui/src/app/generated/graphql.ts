import gql from 'graphql-tag';
import { Injectable } from '@angular/core';
import * as Apollo from 'apollo-angular';
export type Maybe<T> = T | null;
export type Exact<T extends { [key: string]: unknown }> = { [K in keyof T]: T[K] };
export type MakeOptional<T, K extends keyof T> = Omit<T, K> & { [SubKey in K]?: Maybe<T[SubKey]> };
export type MakeMaybe<T, K extends keyof T> = Omit<T, K> & { [SubKey in K]: Maybe<T[SubKey]> };
/** All built-in and custom scalars, mapped to their actual values */
export type Scalars = {
  ID: string;
  String: string;
  Boolean: boolean;
  Int: number;
  Float: number;
};



export type ChampionnatDto = {
  id: Maybe<Scalars['Int']>;
  nom: Scalars['String'];
  saisons: Maybe<Array<SaisonDto>>;
};

export type ChampionnatDtoInput = {
  id: Maybe<Scalars['Int']>;
  nom: Scalars['String'];
  saisons: Maybe<Array<SaisonDtoInput>>;
};

export type ClassementDto = {
  bc: Scalars['Int'];
  bp: Scalars['Int'];
  d: Scalars['Int'];
  diff: Scalars['Int'];
  equipe: EquipeDto;
  mj: Scalars['Int'];
  n: Scalars['Int'];
  pts: Scalars['Int'];
  v: Scalars['Int'];
};

export type ClassementJoueurDto = {
  joueur: JoueurDto;
  nbButs: Scalars['Int'];
  nbCartonsJaunes: Scalars['Int'];
  nbCartonsRouges: Scalars['Int'];
  nbMatchs: Scalars['Int'];
  nbPasses: Scalars['Int'];
  ratioBut: Scalars['Float'];
};

export type EquipeDto = {
  championnat: Maybe<ChampionnatDto>;
  id: Maybe<Scalars['Int']>;
  nom: Scalars['String'];
};

export type EquipeDtoInput = {
  championnat: Maybe<ChampionnatDtoInput>;
  id: Maybe<Scalars['Int']>;
  nom: Scalars['String'];
};

export type JoueurDto = {
  dateNaissance: Scalars['String'];
  equipe: Maybe<EquipeDto>;
  id: Maybe<Scalars['Int']>;
  nationalite: Scalars['String'];
  nom: Scalars['String'];
  poids: Scalars['Int'];
  poste: Scalars['String'];
  prenom: Maybe<Scalars['String']>;
  taille: Scalars['Int'];
};

export type JoueurDtoInput = {
  dateNaissance: Scalars['String'];
  equipe: Maybe<EquipeDtoInput>;
  id: Maybe<Scalars['Int']>;
  nationalite: Scalars['String'];
  nom: Scalars['String'];
  poids: Scalars['Int'];
  poste: Scalars['String'];
  prenom: Maybe<Scalars['String']>;
  taille: Scalars['Int'];
};

export type JoueurStatDto = {
  joueur: JoueurDto;
  nbButs: Scalars['Int'];
  nbCartonsJaunes: Scalars['Int'];
  nbCartonsRouges: Scalars['Int'];
  nbPasses: Scalars['Int'];
};

export type JoueurStatDtoInput = {
  joueur: JoueurDtoInput;
  nbButs: Scalars['Int'];
  nbCartonsJaunes: Scalars['Int'];
  nbCartonsRouges: Scalars['Int'];
  nbPasses: Scalars['Int'];
};

export type JourneeDto = {
  matchs: Array<MatchDto>;
  numero: Scalars['Int'];
};

export type JourneeDtoInput = {
  matchs: Array<MatchDtoInput>;
  numero: Scalars['Int'];
};

export type MatchDto = {
  butDomicile: Maybe<Scalars['Int']>;
  butExterieur: Maybe<Scalars['Int']>;
  date: Scalars['String'];
  domicile: EquipeDto;
  exterieur: EquipeDto;
  id: Maybe<Scalars['Int']>;
  joueurs: Array<JoueurStatDto>;
};

export type MatchDtoInput = {
  butDomicile: Maybe<Scalars['Int']>;
  butExterieur: Maybe<Scalars['Int']>;
  date: Scalars['String'];
  domicile: EquipeDtoInput;
  exterieur: EquipeDtoInput;
  id: Maybe<Scalars['Int']>;
  joueurs: Array<JoueurStatDtoInput>;
};

export type Mutation = {
  calendrier: SaisonDto;
  championnat: ChampionnatDto;
  deleteChampionnat: ChampionnatDto;
  deleteEquipe: EquipeDto;
  equipe: EquipeDto;
  deleteJoueur: JoueurDto;
  joueur: JoueurDto;
  transfert: Array<JoueurDto>;
  match: MatchDto;
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


export type MutationTransfertArgs = {
  joueurIds: Array<Scalars['Int']>;
  equipeId: Maybe<Scalars['Int']>;
};


export type MutationMatchArgs = {
  match: MatchDtoInput;
};

export type Query = {
  championnat: ChampionnatDto;
  championnats: Array<ChampionnatDto>;
  classement: Array<ClassementDto>;
  classementJoueur: Array<ClassementJoueurDto>;
  saison: SaisonDto;
  equipe: EquipeDto;
  equipes: Array<EquipeDto>;
  equipesOfChampionnat: Array<EquipeDto>;
  joueur: JoueurDto;
  joueurs: Array<JoueurDto>;
  joueursByEquipe: Array<JoueurDto>;
  matchsByEquipeAndCurrentSaison: Array<MatchDto>;
  up: Scalars['String'];
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
  equipeId: Maybe<Scalars['Int']>;
};


export type QueryMatchsByEquipeAndCurrentSaisonArgs = {
  equipeId: Scalars['Int'];
};

export type SaisonDto = {
  annee: Scalars['Int'];
  journees: Array<JourneeDto>;
  matchs: Array<MatchDto>;
};

export type SaisonDtoInput = {
  annee: Scalars['Int'];
  journees: Array<JourneeDtoInput>;
  matchs: Array<MatchDtoInput>;
};

export type ChampionnatMutationVariables = Exact<{
  championnat: ChampionnatDtoInput;
}>;


export type ChampionnatMutation = { championnat: Pick<ChampionnatDto, 'id' | 'nom'> };

export type DeleteChampionnatMutationVariables = Exact<{
  id: Scalars['Int'];
}>;


export type DeleteChampionnatMutation = { deleteChampionnat: Pick<ChampionnatDto, 'id' | 'nom'> };

export type CalendrierMutationVariables = Exact<{
  championnatId: Scalars['Int'];
  dateDebut: Scalars['String'];
}>;


export type CalendrierMutation = { calendrier: (
    Pick<SaisonDto, 'annee'>
    & { journees: Array<(
      Pick<JourneeDto, 'numero'>
      & { matchs: Array<(
        Pick<MatchDto, 'id' | 'butDomicile' | 'butExterieur' | 'date'>
        & { domicile: Pick<EquipeDto, 'id' | 'nom'>, exterieur: Pick<EquipeDto, 'id' | 'nom'> }
      )> }
    )> }
  ) };

export type ChampionnatsQueryVariables = Exact<{ [key: string]: never; }>;


export type ChampionnatsQuery = { championnats: Array<(
    Pick<ChampionnatDto, 'id' | 'nom'>
    & { saisons: Maybe<Array<Pick<SaisonDto, 'annee'>>> }
  )> };

export type ChampionnatByIdQueryVariables = Exact<{
  id: Scalars['Int'];
}>;


export type ChampionnatByIdQuery = { championnat: (
    Pick<ChampionnatDto, 'id' | 'nom'>
    & { saisons: Maybe<Array<(
      Pick<SaisonDto, 'annee'>
      & { journees: Array<{ matchs: Array<(
          Pick<MatchDto, 'butDomicile' | 'butExterieur' | 'date'>
          & { domicile: Pick<EquipeDto, 'id' | 'nom'>, exterieur: Pick<EquipeDto, 'id' | 'nom'> }
        )> }> }
    )>> }
  ) };

export type SaisonsQueryVariables = Exact<{
  championnatId: Scalars['Int'];
}>;


export type SaisonsQuery = { championnat: { saisons: Maybe<Array<Pick<SaisonDto, 'annee'>>> } };

export type SaisonQueryVariables = Exact<{
  championnatId: Scalars['Int'];
  saison: Scalars['Int'];
}>;


export type SaisonQuery = { saison: (
    Pick<SaisonDto, 'annee'>
    & { journees: Array<{ matchs: Array<(
        Pick<MatchDto, 'id' | 'butDomicile' | 'butExterieur' | 'date'>
        & { joueurs: Array<(
          Pick<JoueurStatDto, 'nbButs' | 'nbPasses' | 'nbCartonsJaunes' | 'nbCartonsRouges'>
          & { joueur: JoueurFragment }
        )>, domicile: Pick<EquipeDto, 'id' | 'nom'>, exterieur: Pick<EquipeDto, 'id' | 'nom'> }
      )> }> }
  ) };

export type ClassementQueryVariables = Exact<{
  championnatId: Scalars['Int'];
  saison: Scalars['Int'];
}>;


export type ClassementQuery = { classement: Array<(
    Pick<ClassementDto, 'v' | 'n' | 'd' | 'bp' | 'bc' | 'mj' | 'pts' | 'diff'>
    & { equipe: Pick<EquipeDto, 'id' | 'nom'> }
  )> };

export type ClassementJoueurQueryVariables = Exact<{
  championnatId: Scalars['Int'];
  saison: Scalars['Int'];
}>;


export type ClassementJoueurQuery = { classementJoueur: Array<(
    Pick<ClassementJoueurDto, 'nbButs' | 'nbPasses' | 'nbCartonsJaunes' | 'nbCartonsRouges' | 'nbMatchs' | 'ratioBut'>
    & { joueur: Pick<JoueurDto, 'id' | 'nom' | 'prenom'> }
  )> };

export type EquipeMutationVariables = Exact<{
  equipe: EquipeDtoInput;
}>;


export type EquipeMutation = { equipe: Pick<EquipeDto, 'id' | 'nom'> };

export type DeleteEquipeMutationVariables = Exact<{
  id: Scalars['Int'];
}>;


export type DeleteEquipeMutation = { deleteEquipe: Pick<EquipeDto, 'id' | 'nom'> };

export type EquipesQueryVariables = Exact<{ [key: string]: never; }>;


export type EquipesQuery = { equipes: Array<(
    Pick<EquipeDto, 'id' | 'nom'>
    & { championnat: Maybe<Pick<ChampionnatDto, 'id' | 'nom'>> }
  )> };

export type EquipesOfChampionnatQueryVariables = Exact<{
  championnatId: Scalars['Int'];
}>;


export type EquipesOfChampionnatQuery = { equipesOfChampionnat: Array<Pick<EquipeDto, 'id' | 'nom'>> };

export type JoueurWithoutEquipeFragment = Pick<JoueurDto, 'id' | 'nom' | 'prenom' | 'poste' | 'nationalite' | 'dateNaissance' | 'taille' | 'poids'>;

export type JoueurFragment = (
  Pick<JoueurDto, 'id' | 'nom' | 'prenom' | 'poste' | 'nationalite' | 'dateNaissance' | 'taille' | 'poids'>
  & { equipe: Maybe<Pick<EquipeDto, 'id' | 'nom'>> }
);

export type JoueurMutationVariables = Exact<{
  joueur: JoueurDtoInput;
}>;


export type JoueurMutation = { joueur: Pick<JoueurDto, 'id' | 'nom'> };

export type DeleteJoueurMutationVariables = Exact<{
  id: Scalars['Int'];
}>;


export type DeleteJoueurMutation = { deleteJoueur: Pick<JoueurDto, 'id' | 'nom'> };

export type TransfertMutationVariables = Exact<{
  joueurIds: Array<Scalars['Int']>;
  equipeId: Maybe<Scalars['Int']>;
}>;


export type TransfertMutation = { transfert: Array<Pick<JoueurDto, 'id' | 'nom'>> };

export type JoueursQueryVariables = Exact<{ [key: string]: never; }>;


export type JoueursQuery = { joueurs: Array<(
    Pick<JoueurDto, 'id' | 'nom' | 'prenom' | 'poste' | 'nationalite' | 'dateNaissance' | 'taille' | 'poids'>
    & { equipe: Maybe<Pick<EquipeDto, 'id' | 'nom'>> }
  )> };

export type JoueursByEquipeQueryVariables = Exact<{
  equipeId: Maybe<Scalars['Int']>;
}>;


export type JoueursByEquipeQuery = { joueursByEquipe: Array<JoueurWithoutEquipeFragment> };

export type MatchMutationVariables = Exact<{
  match: MatchDtoInput;
}>;


export type MatchMutation = { match: (
    Pick<MatchDto, 'id' | 'date' | 'butDomicile' | 'butExterieur'>
    & { domicile: Pick<EquipeDto, 'id' | 'nom'>, exterieur: Pick<EquipeDto, 'id' | 'nom'> }
  ) };

export type MatchsByEquipeAndCurrentSaisonQueryVariables = Exact<{
  equipeId: Scalars['Int'];
}>;


export type MatchsByEquipeAndCurrentSaisonQuery = { matchsByEquipeAndCurrentSaison: Array<(
    Pick<MatchDto, 'id' | 'butDomicile' | 'butExterieur' | 'date'>
    & { domicile: Pick<EquipeDto, 'id' | 'nom'>, exterieur: Pick<EquipeDto, 'id' | 'nom'>, joueurs: Array<(
      Pick<JoueurStatDto, 'nbButs' | 'nbPasses' | 'nbCartonsJaunes' | 'nbCartonsRouges'>
      & { joueur: (
        Pick<JoueurDto, 'nom'>
        & { equipe: Maybe<Pick<EquipeDto, 'id'>> }
      ) }
    )> }
  )> };

export type UpQueryVariables = Exact<{ [key: string]: never; }>;


export type UpQuery = Pick<Query, 'up'>;

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
export const JoueurFragmentDoc = gql`
    fragment Joueur on JoueurDto {
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
        id
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
        id
        butDomicile
        butExterieur
        date
        joueurs {
          joueur {
            ...Joueur
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
    ${JoueurFragmentDoc}`;

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
      id
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
export const ClassementJoueurDocument = gql`
    query classementJoueur($championnatId: Int!, $saison: Int!) {
  classementJoueur(championnatId: $championnatId, saison: $saison) {
    joueur {
      id
      nom
      prenom
    }
    nbButs
    nbPasses
    nbCartonsJaunes
    nbCartonsRouges
    nbMatchs
    ratioBut
  }
}
    `;

  @Injectable({
    providedIn: 'root'
  })
  export class ClassementJoueurGQL extends Apollo.Query<ClassementJoueurQuery, ClassementJoueurQueryVariables> {
    document = ClassementJoueurDocument;
    
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
export const TransfertDocument = gql`
    mutation transfert($joueurIds: [Int!]!, $equipeId: Int) {
  transfert(joueurIds: $joueurIds, equipeId: $equipeId) {
    id
    nom
  }
}
    `;

  @Injectable({
    providedIn: 'root'
  })
  export class TransfertGQL extends Apollo.Mutation<TransfertMutation, TransfertMutationVariables> {
    document = TransfertDocument;
    
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
    query joueursByEquipe($equipeId: Int) {
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
    id
    date
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
  }
}
    `;

  @Injectable({
    providedIn: 'root'
  })
  export class MatchGQL extends Apollo.Mutation<MatchMutation, MatchMutationVariables> {
    document = MatchDocument;
    
  }
export const MatchsByEquipeAndCurrentSaisonDocument = gql`
    query matchsByEquipeAndCurrentSaison($equipeId: Int!) {
  matchsByEquipeAndCurrentSaison(equipeId: $equipeId) {
    id
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
    joueurs {
      joueur {
        nom
        equipe {
          id
        }
      }
      nbButs
      nbPasses
      nbCartonsJaunes
      nbCartonsRouges
    }
  }
}
    `;

  @Injectable({
    providedIn: 'root'
  })
  export class MatchsByEquipeAndCurrentSaisonGQL extends Apollo.Query<MatchsByEquipeAndCurrentSaisonQuery, MatchsByEquipeAndCurrentSaisonQueryVariables> {
    document = MatchsByEquipeAndCurrentSaisonDocument;
    
  }
export const UpDocument = gql`
    query up {
  up
}
    `;

  @Injectable({
    providedIn: 'root'
  })
  export class UpGQL extends Apollo.Query<UpQuery, UpQueryVariables> {
    document = UpDocument;
    
  }