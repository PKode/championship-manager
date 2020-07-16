import {Component, Inject} from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {ChampionnatDto, MatchDto} from "../../../generated/graphql";

@Component({
  selector: 'app-match-form',
  templateUrl: './match-form.component.html',
  styleUrls: ['./match-form.component.scss']
})
export class MatchFormComponent {
  matchForm = this.fb.group({
    butDomicile: [null, Validators.required],
    butExterieur: [null, Validators.required],
  });

  constructor(private fb: FormBuilder,
              @Inject(MAT_DIALOG_DATA) public data: MatchDto)
  {}

  onSubmit() {
    alert('Thanks!');
  }
}
