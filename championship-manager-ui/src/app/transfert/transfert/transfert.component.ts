import {Component, OnInit, ViewChild} from '@angular/core';
import {map} from 'rxjs/operators';
import {BreakpointObserver, Breakpoints} from '@angular/cdk/layout';
import {JoueurListComponent} from "../../joueur/joueur-list/joueur-list.component";
import {EquipeService} from "../../equipe/equipe.service";
import {EquipeDto} from "../../generated/graphql";
import {JoueurService} from "../../joueur/joueur.service";
import {TransfertJoueurComponent} from "../transfert-joueur/transfert-joueur.component";

@Component({
  selector: 'app-transfert',
  templateUrl: './transfert.component.html',
  styleUrls: ['./transfert.component.scss']
})
export class TransfertComponent implements OnInit {

  @ViewChild(TransfertJoueurComponent) left;
  @ViewChild(TransfertJoueurComponent) right;

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

  sansClubEquipe = {nom: 'Sans Club'} as EquipeDto;

  constructor(private breakpointObserver: BreakpointObserver,
              private equipeService: EquipeService,
              private joueurService: JoueurService) {
  }

  ngOnInit(): void {
    this.equipeService.getAllEquipes().subscribe(data => {
        this.equipes = [this.sansClubEquipe, ...data.map(e => e as EquipeDto)];
        this.leftEquipe = this.equipes[1];
        this.rightEquipe = this.equipes[2];
      }
    );
  }

  equipesEquals(equipe1: EquipeDto, equipe2: EquipeDto) {
    return equipe1.id == equipe2.id
  }

  transfert(from: TransfertJoueurComponent, toEquipe: EquipeDto, fromEquipe: EquipeDto) {
    this.joueurService.transfertJoueurs(from.selectedJoueur.map(j => j.id), toEquipe.id, fromEquipe.id)
    this.left.selectedJoueur = []
    this.right.selectedJoueur = []
    this.left.allSelected = false;
    this.right.allSelected = false;
  }
}
