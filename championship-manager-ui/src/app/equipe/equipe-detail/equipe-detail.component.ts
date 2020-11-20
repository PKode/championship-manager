import {Component} from '@angular/core';
import {JoueurListComponent} from "../../joueur/joueur-list/joueur-list.component";
import {EquipeMatchsComponent} from "../equipe-matchs/equipe-matchs.component";

@Component({
  selector: 'app-equipe-detail',
  templateUrl: './equipe-detail.component.html',
  styleUrls: ['./equipe-detail.component.scss']
})
export class EquipeDetailComponent {
  /** Based on the screen size, switch from standard to one column per row */
  cards = [
    {title: 'Joueurs', component: JoueurListComponent},
    {title: 'Calendrier', component: EquipeMatchsComponent}
  ]

  constructor() {
  }
}
