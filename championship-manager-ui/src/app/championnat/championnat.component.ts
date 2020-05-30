import {Component, OnInit} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {ChampionnatFormComponent} from "./championnat-form/championnat-form.component";

@Component({
  selector: 'app-championnat',
  templateUrl: './championnat.component.html',
  styleUrls: ['./championnat.component.scss']
})
export class ChampionnatComponent implements OnInit {

  constructor(private dialog: MatDialog) {
  }

  ngOnInit(): void {
  }

  add() {
    const dialogRef = this.dialog.open(ChampionnatFormComponent, {
      width: '250px'
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }
}
