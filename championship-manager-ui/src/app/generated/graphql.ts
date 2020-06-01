import gql from 'graphql-tag';
import { Injectable } from '@angular/core';
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
};

export type ChampionnatDtoInput = {
  readonly id: Maybe<Scalars['Int']>;
  readonly nom: Scalars['String'];
};

export type EquipeDto = {
  readonly __typename?: 'EquipeDto';
  readonly id: Maybe<Scalars['Int']>;
  readonly nom: Scalars['String'];
};

export type EquipeDtoInput = {
  readonly id: Maybe<Scalars['Int']>;
  readonly nom: Scalars['String'];
};

export type Mutation = {
  readonly __typename?: 'Mutation';
  readonly championnat: ChampionnatDto;
  readonly deleteChampionnat: ChampionnatDto;
  readonly deleteEquipe: EquipeDto;
  readonly equipe: EquipeDto;
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

export type Query = {
  readonly __typename?: 'Query';
  readonly championnat: ChampionnatDto;
  readonly championnats: ReadonlyArray<ChampionnatDto>;
  readonly equipe: EquipeDto;
  readonly equipes: ReadonlyArray<EquipeDto>;
};


export type QueryChampionnatArgs = {
  id: Scalars['Int'];
};


export type QueryEquipeArgs = {
  id: Scalars['Int'];
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

export type ChampionnatsQueryVariables = {};


export type ChampionnatsQuery = (
  { readonly __typename?: 'Query' }
  & { readonly championnats: ReadonlyArray<(
    { readonly __typename?: 'ChampionnatDto' }
    & Pick<ChampionnatDto, 'id' | 'nom'>
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
  )> }
);

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
export const ChampionnatsDocument = gql`
    query championnats {
  championnats {
    id
    nom
  }
}
    `;

  @Injectable({
    providedIn: 'root'
  })
  export class ChampionnatsGQL extends Apollo.Query<ChampionnatsQuery, ChampionnatsQueryVariables> {
    document = ChampionnatsDocument;
    
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
  }
}
    `;

  @Injectable({
    providedIn: 'root'
  })
  export class EquipesGQL extends Apollo.Query<EquipesQuery, EquipesQueryVariables> {
    document = EquipesDocument;
    
  }