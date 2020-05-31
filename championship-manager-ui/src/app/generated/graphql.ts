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

export type Mutation = {
  readonly __typename?: 'Mutation';
  readonly championnat: ChampionnatDto;
  readonly deleteChampionnat: ChampionnatDto;
};


export type MutationChampionnatArgs = {
  championnat: ChampionnatDtoInput;
};


export type MutationDeleteChampionnatArgs = {
  id: Scalars['Int'];
};

export type Query = {
  readonly __typename?: 'Query';
  readonly championnat: ChampionnatDto;
  readonly championnats: ReadonlyArray<ChampionnatDto>;
};


export type QueryChampionnatArgs = {
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