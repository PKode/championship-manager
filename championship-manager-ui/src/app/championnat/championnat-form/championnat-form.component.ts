import {Component, Inject} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {ChampionnatService} from "../championnat.service";
import {Championnat} from "../championnat";
import {ChampionnatDto} from "../../generated/graphql";

@Component({
  selector: 'app-championnat-form',
  templateUrl: './championnat-form.component.html',
  styleUrls: ['./championnat-form.component.scss']
})
export class ChampionnatFormComponent {
  championnatForm: FormGroup;

  constructor(private fb: FormBuilder,
              private dialogRef: MatDialogRef<ChampionnatFormComponent>,
              private championnatService: ChampionnatService,
              @Inject(MAT_DIALOG_DATA) public data: ChampionnatDto) {
    this.championnatForm = this.fb.group({
      nom: [data?.nom ? data.nom : null, Validators.required]
    });
  }

  onSubmit() {
    this.championnatService.createOrUpdateChampionnat(new Championnat(this.championnatForm.value.nom, this.data?.id));
    this.dialogRef.close();
  }
}
