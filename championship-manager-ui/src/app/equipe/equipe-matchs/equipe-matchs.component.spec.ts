import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {NoopAnimationsModule} from '@angular/platform-browser/animations';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatSortModule} from '@angular/material/sort';
import {MatTableModule} from '@angular/material/table';

import {EquipeMatchsComponent} from './equipe-matchs.component';

describe('EquipeMatchsComponent', () => {
  let component: EquipeMatchsComponent;
  let fixture: ComponentFixture<EquipeMatchsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EquipeMatchsComponent ],
      imports: [
        NoopAnimationsModule,
        MatPaginatorModule,
        MatSortModule,
        MatTableModule,
      ]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EquipeMatchsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should compile', () => {
    expect(component).toBeTruthy();
  });
});
