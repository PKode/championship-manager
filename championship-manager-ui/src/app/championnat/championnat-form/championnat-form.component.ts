import {Component} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {MatDialogRef} from "@angular/material/dialog";
import {ChampionnatGQL, ChampionnatsGQL} from "../../generated/graphql";

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
              private championnatMutation: ChampionnatGQL,
              private championnatQuery: ChampionnatsGQL) {
  }

  onSubmit() {
    console.log(this.championnatForm.value.nom);
    this.championnatMutation.mutate({nom: this.championnatForm.value.nom}, {
        refetchQueries: [{
          query: this.championnatQuery.document
        }]
      }
    ).subscribe();
    this.dialogRef.close();
  }
}
