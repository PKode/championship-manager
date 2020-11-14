import {Component, Input, OnInit} from '@angular/core';
import {JoueurStatDto} from "../../../../generated/graphql";
import {FormGroup} from "@angular/forms";

@Component({
  selector: 'app-joueur-stat',
  templateUrl: './joueur-stat.component.html',
  styleUrls: ['./joueur-stat.component.scss']
})
export class JoueurStatComponent implements OnInit {

  @Input()
  joueursStat: JoueurStatDto[]

  @Input()
  matchForm: FormGroup

  @Input()
  mathFormControlName: string

  @Input()
  camp: string;

  constructor() { }

  ngOnInit(): void {
  }


  incrementStats(joueur: JoueurStatDto, camp: string, stat: string) : void{
    let totalDomicile = sumBy(this.matchForm.value.selectedJoueursDomicile, stat);
    let totalExterieur = sumBy(this.matchForm.value.selectedJoueursExterieur, stat);
    if (camp == 'dom' && this.canScoreMore(totalDomicile, this.matchForm.value.butDomicile, joueur))
      stat == 'nbButs' ? joueur.nbButs++ : joueur.nbPasses++;
    if (camp == 'ext' && this.canScoreMore(totalExterieur, this.matchForm.value.butExterieur, joueur))
      stat == 'nbButs' ? joueur.nbButs++ : joueur.nbPasses++;
  }

  decrementStats(joueur: JoueurStatDto, stat: string) {
    if (stat == 'nbButs' && joueur.nbButs > 0) joueur.nbButs--
    if (stat == 'nbPasses' && joueur.nbPasses > 0) joueur.nbPasses--
    if (stat == 'nbCartonsJaunes' && joueur.nbCartonsJaunes > 0) {
      joueur.nbCartonsJaunes--;
      joueur.nbCartonsRouges = 0;
    }
    if (stat == 'nbCartonsRouges' && joueur.nbCartonsRouges > 0) joueur.nbCartonsRouges--
  }

  canScoreMore(totalEquipe: number, butEquipe: number, joueur: JoueurStatDto) {
    return (totalEquipe < butEquipe && (joueur.nbButs + joueur.nbPasses) < butEquipe)
  }

  incrementCartonsJaunes(joueur: JoueurStatDto) {
    if (joueur.nbCartonsJaunes < 2)
      joueur.nbCartonsJaunes++;
    if (joueur.nbCartonsJaunes == 2) joueur.nbCartonsRouges = 1
  }

  incrementCartonsRouges(joueur: JoueurStatDto) {
    if (joueur.nbCartonsRouges < 1)
      joueur.nbCartonsRouges++;
  }

  joueurStatEquals(joueur1: JoueurStatDto, joueur2: JoueurStatDto) {
    return joueur1.joueur.id == joueur2.joueur.id;
  }s
}

const sumBy = <T = any>(arr: T[], fn: string | ((a: T) => number)) => {
  return arr
    .map(typeof fn === "function" ? fn : (val: any) => val[fn])
    .reduce((acc, val) => acc + val, 0);
};
