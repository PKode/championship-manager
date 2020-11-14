import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {JoueurStatComponent} from './joueur-stat.component';

describe('JoueurStatComponent', () => {
  let component: JoueurStatComponent;
  let fixture: ComponentFixture<JoueurStatComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ JoueurStatComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(JoueurStatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
