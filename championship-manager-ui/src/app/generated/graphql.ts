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
  /** Long type */
  Long: number;
};



export type ChampionnatDto = {
  readonly __typename?: 'ChampionnatDto';
  readonly id: Maybe<Scalars['Long']>;
  readonly nom: Scalars['String'];
};


export type Mutation = {
  readonly __typename?: 'Mutation';
  readonly championnat: ChampionnatDto;
  readonly deleteChampionnat: ChampionnatDto;
};


export type MutationChampionnatArgs = {
  nom: Scalars['String'];
};


export type MutationDeleteChampionnatArgs = {
  id: Scalars['Long'];
};

export type Query = {
  readonly __typename?: 'Query';
  readonly championnat: ChampionnatDto;
  readonly championnats: ReadonlyArray<ChampionnatDto>;
};


export type QueryChampionnatArgs = {
  id: Scalars['Long'];
};

export type ChampionnatsQueryVariables = {};


export type ChampionnatsQuery = (
  { readonly __typename?: 'Query' }
  & { readonly championnats: ReadonlyArray<(
    { readonly __typename?: 'ChampionnatDto' }
    & Pick<ChampionnatDto, 'id' | 'nom'>
  )> }
);

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