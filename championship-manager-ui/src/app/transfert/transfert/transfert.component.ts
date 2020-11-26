import {Component, OnInit} from '@angular/core';
import {map} from 'rxjs/operators';
import {BreakpointObserver, Breakpoints} from '@angular/cdk/layout';
import {JoueurListComponent} from "../../joueur/joueur-list/joueur-list.component";
import {EquipeService} from "../../equipe/equipe.service";
import {EquipeDto} from "../../generated/graphql";

@Component({
  selector: 'app-transfert',
  templateUrl: './transfert.component.html',
  styleUrls: ['./transfert.component.scss']
})
export class TransfertComponent implements OnInit {
  /** Based on the screen size, switch from standard to one column per row */
  cards = this.breakpointObserver.observe(Breakpoints.Handset).pipe(
    map(({matches}) => {
      if (matches) {
        return [
          {cols: 1, rows: 1, component: JoueurListComponent},
          {cols: 1, rows: 1, component: JoueurListComponent}
        ];
      }

      return [
        {cols: 1, rows: 2, component: JoueurListComponent},
        {cols: 1, rows: 2, component: JoueurListComponent}
      ];
    })
  );

  equipes: EquipeDto[] = [];
  leftEquipe: EquipeDto;
  rightEquipe: EquipeDto;

  constructor(private breakpointObserver: BreakpointObserver,
              private equipeService: EquipeService) {
  }

  ngOnInit(): void {
    this.equipeService.getAllEquipes().subscribe(data => {
        this.equipes = data.map(e => e as EquipeDto);
        this.leftEquipe = this.equipes[0];
        this.rightEquipe = this.equipes[1];
      }
    );
  }

  equipesEquals(equipe1: EquipeDto, equipe2: EquipeDto) {
    return equipe1.id == equipe2.id
  }
}
