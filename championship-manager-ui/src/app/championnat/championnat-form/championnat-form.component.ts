import {Component} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {MatDialogRef} from "@angular/material/dialog";
import {ChampionnatService} from "../championnat.service";

@Component({
  selector: 'app-championnat-form',
  templateUrl: './championnat-form.component.html',
  styleUrls: ['./championnat-form.component.scss']
})
export class ChampionnatFormComponent {
  championnatForm = this.fb.group({
    nom: [null, Validators.required]
  });

  constructor(private fb: FormBuilder,
              private dialogRef: MatDialogRef<ChampionnatFormComponent>,
              private championnatService: ChampionnatService) {
  }

  onSubmit() {
    this.championnatService.createChampionnat(this.championnatForm.value.nom);
    this.dialogRef.close();
  }
}
