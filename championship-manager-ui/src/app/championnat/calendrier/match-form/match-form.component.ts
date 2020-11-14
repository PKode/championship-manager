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
    joueursExterieur: JoueurStatDto[] = [];

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
                this.joueursDomicile.push(...this.data.match.joueurs.filter(j=>j.joueur.equipe.id == this.data.match.domicile.id))
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
                this.joueursExterieur.push(...this.data.match.joueurs.filter(j=>j.joueur.equipe.id == this.data.match.exterieur.id))
                joueurs.forEach(j => {
                    if (this.joueursExterieur.find(it => this.joueurStatEquals(this.toJoueurStat(j), it)) === undefined)
                        this.joueursExterieur.push(this.toJoueurStat(j))
                });
                this.matchForm.patchValue({
                    selectedJoueursExterieur: this.joueursExterieur.filter(j =>
                        this.data.match.joueurs.find(it => this.joueurStatEquals(j, it)) != undefined
                    )
                });
            });
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
}
