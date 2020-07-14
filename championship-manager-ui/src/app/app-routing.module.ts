import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ChampionnatComponent} from "./championnat/championnat.component";
import {EquipeComponent} from "./equipe/equipe.component";
import {ChampionnatDetailComponent} from "./championnat/championnat-detail/championnat-detail.component";


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
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
