import {Component, OnInit} from '@angular/core';
import {HealthService} from "./health.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'championship-manager-ui';
  loading = true;

  constructor(private healthService: HealthService) {
  }

  ngOnInit() {
    this.healthService.upStatus().subscribe(data => {
      this.loading = false;
    })
  }
}
