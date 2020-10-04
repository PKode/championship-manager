import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {EquipeService} from "../equipe.service";
import {Equipe} from "../equipe";
import {ChampionnatService} from "../../championnat/championnat.service";
import {Championnat} from "../../championnat/championnat";
import {ChampionnatDto} from "../../generated/graphql";

@Component({
  selector: 'app-equipe-form',
  templateUrl: './equipe-form.component.html',
  styleUrls: ['./equipe-form.component.scss']
})
export class EquipeFormComponent implements OnInit {
  equipeForm: FormGroup;
  championnats: Championnat[];

  constructor(private fb: FormBuilder,
              private dialogRef: MatDialogRef<EquipeFormComponent>,
              private equipeService: EquipeService,
              private championnatService: ChampionnatService,
              @Inject(MAT_DIALOG_DATA) public data: Equipe) {

    this.equipeForm = this.fb.group({
      nom: [data?.nom ? data.nom : null, Validators.required],
      championnat: [data?.championnat ? data.championnat : null, Validators.required]
    });
  }

  onSubmit() {
    this.equipeService.createEquipe(new Equipe(this.equipeForm.value.nom, this.equipeForm.value.championnat, this.data?.id));
    this.dialogRef.close();
  }

  ngOnInit(): void {
    this.championnatService.getAllChampionnats()
      .subscribe(value => this.championnats = value.map(d => new Championnat(d.nom, d.id)))
  }

  championnatEquals(championnat1: ChampionnatDto, championnat2: ChampionnatDto) {
    return championnat1.id == championnat2.id
  }
}
