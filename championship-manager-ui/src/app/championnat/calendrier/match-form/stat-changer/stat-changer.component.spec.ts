import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {StatChangerComponent} from './stat-changer.component';

describe('StatChangerComponent', () => {
  let component: StatChangerComponent;
  let fixture: ComponentFixture<StatChangerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StatChangerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StatChangerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
