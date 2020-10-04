import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {HttpClient} from "@angular/common/http";
import {JoueurService} from "../joueur.service";
import {EquipeService} from "../../equipe/equipe.service";
import {EquipeDto, JoueurDto} from "../../generated/graphql";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import * as moment from "moment";

class Pays {
  constructor(fr: String, flag: String) {
    this.name = fr;
    this.flag = flag;
  }

  name: String;
  flag: String
}

@Component({
  selector: 'app-joueur-form',
  templateUrl: './joueur-form.component.html',
  styleUrls: ['./joueur-form.component.scss']
})
export class JoueurFormComponent implements OnInit {
  joueurForm: FormGroup;

  nationalites: Pays[];

  postes = ['G', 'DC', 'DG', 'DD', 'MDC', 'MDG', 'MDD', 'MOC', 'MOD', 'MOG', 'ATC', 'ATD', 'ATG'];

  equipes: EquipeDto[];

  selectedEquipe: EquipeDto;

  constructor(private fb: FormBuilder,
              private httpClient: HttpClient,
              private joueurService: JoueurService,
              private equipeService: EquipeService,
              private dialogRef: MatDialogRef<JoueurFormComponent>,
              @Inject(MAT_DIALOG_DATA) public data: JoueurDto) {
    this.joueurForm = this.fb.group({
      prenom: [data?.prenom ? data.prenom : null, Validators.required],
      nom: [data?.nom ? data.nom : null, Validators.required],
      equipe: [data?.equipe ? data.equipe : null, Validators.required],
      poste: [data?.poste ? data.poste : null, Validators.required],
      nationalite: [data?.nationalite ? data.nationalite : null, Validators.required],
      dateNaissance: [data?.dateNaissance ? moment(data.dateNaissance, 'DD/MM/YYYY') : null, Validators.required],
      taille: [data?.taille ? data.taille : 0, Validators.required],
      poids: [data?.poids ? data.poids : 0, Validators.required]
    });
  }

  onSubmit() {
    this.joueurService.createOrUpdateJoueur({
        id: this.data?.id,
        nom: this.joueurForm.value.nom,
        prenom: this.joueurForm.value.prenom,
        poste: this.joueurForm.value.poste,
        nationalite: this.joueurForm.value.nationalite,
        dateNaissance: this.joueurForm.value.dateNaissance.format('DD/MM/YYYY'),
        taille: this.joueurForm.value.taille,
        poids: this.joueurForm.value.poids,
        equipe: {id: this.joueurForm.value.equipe.id, nom: this.joueurForm.value.equipe.nom, championnat: null}
      }
    );
    this.dialogRef.close();
  }

  ngOnInit(): void {
    this.httpClient.get<[{ name, flag, translations }]>('https://restcountries.eu/rest/v2/all?fields=name;flag;translations')
      .subscribe(data => this.nationalites = data.map(p => new Pays(p.translations.fr, p.flag)));

    this.equipeService.getAllEquipes()
      .subscribe(data => {
        this.equipes = data.map(e => e as EquipeDto);
      });
  }

  equipeEquals(equipe1: EquipeDto, equipe2: EquipeDto) {
    return equipe1.id == equipe2.id
  }
}
