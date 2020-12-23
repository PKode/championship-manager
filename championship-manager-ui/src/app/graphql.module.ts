import {NgModule} from '@angular/core';
import {APOLLO_OPTIONS, ApolloModule} from 'apollo-angular';
import {HttpLink, HttpLinkModule} from 'apollo-angular-link-http';
import {InMemoryCache} from 'apollo-cache-inmemory';
import {ApolloLink} from "apollo-link";
import {environment} from "../environments/environment";
import {RetryLink} from "apollo-link-retry";

const uri = environment.graphqlUri; // <-- add the URL of the GraphQL server here
export function createApollo(httpLink: HttpLink) {
  return {
    link: ApolloLink.from([typenameMiddleware, retryLink, httpLink.create({uri: uri})]),
    cache: new InMemoryCache(),
  };
}
const typenameMiddleware = new ApolloLink((operation, forward) => {
  if (operation.variables) {
    operation.variables = JSON.parse(JSON.stringify(operation.variables), omitTypename);
  }
  return forward(operation);
});

function omitTypename(key, value) {
  return key === '__typename' ? undefined : value;
}
const retryLink = new RetryLink({
  delay: {
    initial: 1000,
    max: Infinity,
    jitter: false
  },
  attempts: {
    max: 5,
    retryIf: (error, _operation) => !!error
  }
});

@NgModule({
  exports: [ApolloModule, HttpLinkModule],
  providers: [
    {
      provide: APOLLO_OPTIONS,
      useFactory: createApollo,
      deps: [HttpLink],
    },
  ],
})
export class GraphQLModule {
}
