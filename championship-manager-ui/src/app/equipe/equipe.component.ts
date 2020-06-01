import {Component, OnInit} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {EquipeFormComponent} from "./equipe-form/equipe-form.component";

@Component({
  selector: 'app-equipe',
  templateUrl: './equipe.component.html',
  styleUrls: ['./equipe.component.scss']
})
export class EquipeComponent implements OnInit {

  constructor(private dialog: MatDialog) {
  }

  ngOnInit(): void {
  }

  add() {
    const dialogRef = this.dialog.open(EquipeFormComponent, {
      width: '250px'
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }
}
