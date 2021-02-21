import {Component, OnInit} from '@angular/core';
import {JoueurFormComponent} from "./joueur-form/joueur-form.component";
import {MatDialog} from "@angular/material/dialog";
import {JoueurService} from "./joueur.service";
import {JoueursGQL} from "../generated/graphql";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-joueur',
  templateUrl: './joueur.component.html',
  styleUrls: ['./joueur.component.scss']
})
export class JoueurComponent implements OnInit {

  constructor(private dialog: MatDialog,
              private joueurService: JoueurService,
              private joueurQuery: JoueursGQL,
              private snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
  }

  add() {
    const dialogRef = this.dialog.open(JoueurFormComponent, {
      width: '500px'
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  upload($event: any) {
    this.joueurService.upload($event.target.files[0]).subscribe(data => {
        let errors = data.filter(e => e.left !== undefined)
          .map(e => e.left);
        if (errors.length > 0) {
          this.openErroSnackBar(errors)
        } else {
          this.openSuccessSnackBar(data.length);
          this.joueurQuery.watch().refetch();
        }
      }
    )
  }

  private openErroSnackBar(errors: Error[]) {
    this.snackBar.open("Error lors de l'import des joueurs", "Plus d'infos", {
      duration: 2000,
      verticalPosition: 'top',
      panelClass: ['red-snackbar']
    })
  }

  private openSuccessSnackBar(nbImports: number) {
    this.snackBar.open(nbImports + " joueurs importés", "OK", {
      duration: 2000,
      verticalPosition: 'top',
      politeness: 'assertive',
      panelClass: ['green-snackbar']
    })
  }
}
