import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {MatchDto} from "../../../generated/graphql";
import {MatchService} from "../../match.service";
import {ActivatedRoute} from "@angular/router";

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

  championnatId: number;
  constructor(private fb: FormBuilder,
              private matchService: MatchService,
              public dialogRef: MatDialogRef<MatchFormComponent>,
              @Inject(MAT_DIALOG_DATA) public data: {match: MatchDto, championnatId: number, saison: number}) {
    this.matchForm.patchValue({
      butDomicile: this.data.match.butDomicile,
      butExterieur: this.data.match.butExterieur,
    });
  }

  onSubmit() {
    const matchUpdate = JSON.parse(JSON.stringify(this.data.match));
    matchUpdate.butDomicile = this.matchForm.value.butDomicile;
    matchUpdate.butExterieur = this.matchForm.value.butExterieur;
    this.matchService.createOrUpdateMatch(matchUpdate, this.data.championnatId, this.data.saison);
    this.dialogRef.close();
  }
}
