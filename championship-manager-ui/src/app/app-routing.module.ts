import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ChampionnatComponent} from "./championnat/championnat.component";
import {EquipeComponent} from "./equipe/equipe.component";
import {ChampionnatDetailComponent} from "./championnat/championnat-detail/championnat-detail.component";
import {JoueurComponent} from "./joueur/joueur.component";


const routes: Routes = [
  {
    path: '',
    component: ChampionnatComponent
  },
  {
    path: 'championnat',
    component: ChampionnatComponent
  },
  {
    path: 'championnat/:id',
    component: ChampionnatDetailComponent
  },
  {
    path: 'championnat/:id/saison/:saison',
    component: ChampionnatDetailComponent
  },
  {
    path: 'equipe',
    component: EquipeComponent
  },
  {
    path: 'joueur',
    component: JoueurComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
