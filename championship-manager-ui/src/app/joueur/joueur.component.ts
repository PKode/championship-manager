import {Component, OnInit} from '@angular/core';
import {JoueurFormComponent} from "./joueur-form/joueur-form.component";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-joueur',
  templateUrl: './joueur.component.html',
  styleUrls: ['./joueur.component.scss']
})
export class JoueurComponent implements OnInit {

  constructor(private dialog: MatDialog) {
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
}
