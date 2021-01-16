import {Component, OnInit} from '@angular/core';
import {JoueurFormComponent} from "./joueur-form/joueur-form.component";
import {MatDialog} from "@angular/material/dialog";
import {JoueurService} from "./joueur.service";
import {JoueursGQL, JoueursQuery} from "../generated/graphql";

@Component({
  selector: 'app-joueur',
  templateUrl: './joueur.component.html',
  styleUrls: ['./joueur.component.scss']
})
export class JoueurComponent implements OnInit {

  constructor(private dialog: MatDialog,
              private joueurService: JoueurService,
              private joueurQuery: JoueursGQL) {
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
    this.joueurService.upload($event.target.files[0]).subscribe(data => this.joueurQuery.watch().refetch())
  }
}
