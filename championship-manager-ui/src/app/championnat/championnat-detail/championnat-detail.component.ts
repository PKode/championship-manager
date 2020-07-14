import {Component, OnInit} from '@angular/core';
import {map} from 'rxjs/operators';
import {BreakpointObserver, Breakpoints} from '@angular/cdk/layout';
import {CalendrierComponent} from "../calendrier/calendrier.component";
import {EquipeListComponent} from "../../equipe/equipe-list/equipe-list.component";
import {ClassementComponent} from "../classement/classement.component";
import {ClassementButeurComponent} from "../classement-buteur/classement-buteur.component";
import {ChampionnatService} from "../championnat.service";
import {ActivatedRoute, Router} from "@angular/router";
import {SaisonDto} from "../../generated/graphql";

@Component({
  selector: 'app-championnat-detail',
  templateUrl: './championnat-detail.component.html',
  styleUrls: ['./championnat-detail.component.scss']
})
export class ChampionnatDetailComponent implements OnInit {

  saisons: number[];
  selectedSaison: number;
  championnatId: number;
  /** Based on the screen size, switch from standard to one column per row */
  cards = this.breakpointObserver.observe(Breakpoints.Handset).pipe(
    map(({matches}) => {
      if (matches) {
        return [
          {title: 'Calendrier', cols: 1, rows: 1, component: CalendrierComponent},
          {title: 'Classement', cols: 1, rows: 1, component: ClassementComponent},
          {title: 'Classement Buteur', cols: 1, rows: 1, component: ClassementButeurComponent},
          {title: 'Equipes', cols: 1, rows: 1, component: EquipeListComponent}
        ];
      }

      return [
        {title: 'Calendrier', cols: 1, rows: 2, component: CalendrierComponent},
        {title: 'Classement', cols: 1, rows: 2, component: ClassementComponent},
        {title: 'Classement Buteur', cols: 1, rows: 1, component: ClassementButeurComponent},
        {title: 'Equipes', cols: 1, rows: 1, component: EquipeListComponent}
      ];
    })
  );

  constructor(private breakpointObserver: BreakpointObserver,
              private championnatService: ChampionnatService,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.championnatId = Number.parseInt(params.get("id"));
      this.selectedSaison = Number.parseInt(params.get("saison"));
      this.championnatService.getSaisons(this.championnatId).subscribe(data => {
        this.saisons = data.map(it => (it as SaisonDto).annee);
      });
    });
  }

  changeSaison(saison: number) {
    this.selectedSaison = saison;
    this.router.navigate(['/championnat/' + this.championnatId + '/saison/' + saison]).then()
  }
}
