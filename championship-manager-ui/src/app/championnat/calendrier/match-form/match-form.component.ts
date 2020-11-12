import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {JoueurDto, JoueurStatDto, MatchDto} from "../../../generated/graphql";
import {MatchService} from "../../match.service";
import {JoueurService} from "../../../joueur/joueur.service";

@Component({
    selector: 'app-match-form',
    templateUrl: './match-form.component.html',
    styleUrls: ['./match-form.component.scss']
})
export class MatchFormComponent implements OnInit {
    matchForm = this.fb.group({
        butDomicile: [null, Validators.required],
        butExterieur: [null, Validators.required],
        selectedJoueursDomicile: [[]],
        selectedJoueursExterieur: [[]]
    });

    championnatId: number;
    joueursDomicile: JoueurStatDto[] = [];
    joueursExterieur: JoueurStatDto[];

    limitButs: number

    constructor(private fb: FormBuilder,
                private matchService: MatchService,
                private joueurService: JoueurService,
                public dialogRef: MatDialogRef<MatchFormComponent>,
                @Inject(MAT_DIALOG_DATA) public data: { match: MatchDto, championnatId: number, saison: number }) {
        this.matchForm.patchValue({
            butDomicile: this.data.match.butDomicile,
            butExterieur: this.data.match.butExterieur
        });
    }

    ngOnInit() {
        this.joueurService.getAllJoueursByEquipe(this.data.match.domicile.id)
            .subscribe(joueurs => {
                this.joueursDomicile.push(...this.data.match.joueurs)
                joueurs.forEach(j => {
                    if (this.joueursDomicile.find(it => this.joueurStatEquals(this.toJoueurStat(j), it)) === undefined)
                        this.joueursDomicile.push(this.toJoueurStat(j))
                });
                this.matchForm.patchValue({
                    selectedJoueursDomicile: this.joueursDomicile.filter(j =>
                        this.data.match.joueurs.find(it => this.joueurStatEquals(j, it)) != undefined
                    )
                });
            });
        this.joueurService.getAllJoueursByEquipe(this.data.match.exterieur.id)
            .subscribe(joueurs => {
                this.joueursExterieur = joueurs.map(j => this.toJoueurStat(j as JoueurDto))
                this.matchForm.patchValue({
                    selectedJoueursExterieur: this.data.match.joueurs.filter(j =>
                        this.joueursExterieur.find(it => this.joueurStatEquals(j, it))
                    )
                });
            });

        this.limitButs = this.data.match.butDomicile;
    }

    onSubmit() {
        let joueurs = this.matchForm.value.selectedJoueursDomicile.concat(this.matchForm.value.selectedJoueursExterieur);
        this.data.match.butDomicile = this.matchForm.value.butDomicile;
        this.data.match.butExterieur = this.matchForm.value.butExterieur;
        this.data.match.joueurs = joueurs;
        this.matchService.createOrUpdateMatch(this.data.match, this.data.championnatId, this.data.saison);
        this.dialogRef.close();
    }

    joueurStatEquals(joueur1: JoueurStatDto, joueur2: JoueurStatDto) {
        return joueur1.joueur.id == joueur2.joueur.id;
    }

    toJoueurStat(joueur: any): JoueurStatDto {
        return {
            joueur: joueur as JoueurDto,
            nbButs: 0,
            nbPasses: 0,
            nbCartonsJaunes: 0,
            nbCartonsRouges: 0
        }
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
}

const sumBy = <T = any>(arr: T[], fn: string | ((a: T) => number)) => {
    return arr
        .map(typeof fn === "function" ? fn : (val: any) => val[fn])
        .reduce((acc, val) => acc + val, 0);
};
