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
    joueursDomicile: JoueurDto[];
    joueursExterieur: JoueurDto[];

    constructor(private fb: FormBuilder,
                private matchService: MatchService,
                private joueurService: JoueurService,
                public dialogRef: MatDialogRef<MatchFormComponent>,
                @Inject(MAT_DIALOG_DATA) public data: { match: MatchDto, championnatId: number, saison: number }) {
        console.log(this.data.match.joueurs);
        this.matchForm.patchValue({
            butDomicile: this.data.match.butDomicile,
            butExterieur: this.data.match.butExterieur,
            selectedJoueursDomicile: this.data.match.joueurs.map(js => js.joueur),
            selectedJoueursExterieur:this.data.match.joueurs.map(js => js.joueur)
        });
    }

    ngOnInit() {
        this.joueurService.getAllJoueursByEquipe(this.data.match.domicile.id)
            .subscribe(joueurs => this.joueursDomicile = joueurs.map(j => j as JoueurDto));
        this.joueurService.getAllJoueursByEquipe(this.data.match.exterieur.id)
            .subscribe(joueurs => this.joueursExterieur = joueurs.map(j => j as JoueurDto));
    }

    onSubmit() {
        let joueurs = this.matchForm.value.selectedJoueursDomicile.concat(this.matchForm.value.selectedJoueursExterieur)
            .map(j => {
                return {
                    joueur: j,
                    nbButs: 0,
                    nbPasses: 0,
                    nbCartonsJaunes: 0,
                    nbCartonsRouges: 0
                } as JoueurStatDto;
            });
        const matchUpdate = JSON.parse(JSON.stringify(this.data.match));
        matchUpdate.butDomicile = this.matchForm.value.butDomicile;
        matchUpdate.butExterieur = this.matchForm.value.butExterieur;
        matchUpdate.joueurs = joueurs;
        this.matchService.createOrUpdateMatch(matchUpdate, this.data.championnatId, this.data.saison);
        this.dialogRef.close();
    }

    joueurEquals(joueur1: JoueurDto, joueur2: JoueurDto) {
        return joueur1.id == joueur2.id
    }
}
