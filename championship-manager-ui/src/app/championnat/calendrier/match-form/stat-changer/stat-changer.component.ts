import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-stat-changer',
  templateUrl: './stat-changer.component.html',
  styleUrls: ['./stat-changer.component.scss']
})
export class StatChangerComponent implements OnInit {

  constructor() {
  }

  @Input()
  icon: string;

  @Input()
  stat: number;

  @Input()
  iconStyle: string;

  @Output()
  onUp: EventEmitter<any> = new EventEmitter<any>()

  @Output()
  onDown: EventEmitter<any> = new EventEmitter<any>()

  up() {
    this.onUp.emit();
  }

  down() {
    this.onDown.emit();
  }

  ngOnInit(): void {
  }

}
