import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ChampionnatComponent} from "./championnat/championnat.component";
import {EquipeComponent} from "./equipe/equipe.component";


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
    path: 'equipe',
    component: EquipeComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
