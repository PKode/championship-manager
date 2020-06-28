import {Component} from '@angular/core';
import {map} from 'rxjs/operators';
import {BreakpointObserver, Breakpoints} from '@angular/cdk/layout';
import {CalendrierComponent} from "../calendrier/calendrier.component";
import {EquipeListComponent} from "../../equipe/equipe-list/equipe-list.component";

@Component({
  selector: 'app-championnat-detail',
  templateUrl: './championnat-detail.component.html',
  styleUrls: ['./championnat-detail.component.scss']
})
export class ChampionnatDetailComponent {
  /** Based on the screen size, switch from standard to one column per row */
  cards = this.breakpointObserver.observe(Breakpoints.Handset).pipe(
    map(({matches}) => {
      if (matches) {
        return [
          {title: 'Calendrier', cols: 1, rows: 1, component: CalendrierComponent},
          {title: 'Classement', cols: 1, rows: 1, component: EquipeListComponent},
          {title: 'Classement Buteur', cols: 1, rows: 1, component: CalendrierComponent},
          {title: 'Equipes', cols: 1, rows: 1, component: EquipeListComponent}
        ];
      }

      return [
        {title: 'Calendrier', cols: 1, rows: 2, component: CalendrierComponent},
        {title: 'Classement', cols: 1, rows: 2, component: EquipeListComponent},
        {title: 'Classement Buteur', cols: 1, rows: 1, component: CalendrierComponent},
        {title: 'Equipes', cols: 1, rows: 1, component: EquipeListComponent}
      ];
    })
  );

  constructor(private breakpointObserver: BreakpointObserver) {
  }
}
