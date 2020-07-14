import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ClassementButeurComponent } from './classement-buteur.component';

describe('ClassementButeurComponent', () => {
  let component: ClassementButeurComponent;
  let fixture: ComponentFixture<ClassementButeurComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ClassementButeurComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ClassementButeurComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
