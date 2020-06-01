import {Component, Inject} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {EquipeService} from "../equipe.service";
import {Equipe} from "../equipe";

@Component({
  selector: 'app-equipe-form',
  templateUrl: './equipe-form.component.html',
  styleUrls: ['./equipe-form.component.scss']
})
export class EquipeFormComponent {
  equipeForm: FormGroup;

  constructor(private fb: FormBuilder,
              private dialogRef: MatDialogRef<EquipeFormComponent>,
              private equipeService: EquipeService,
              @Inject(MAT_DIALOG_DATA) public data: Equipe) {

    this.equipeForm = this.fb.group({
      nom: [data?.nom ? data.nom : null, Validators.required]
    });
  }

  onSubmit() {
    this.equipeService.createChampionnat(new Equipe(this.equipeForm.value.nom, this.data?.id));
    this.dialogRef.close();
  }
}
