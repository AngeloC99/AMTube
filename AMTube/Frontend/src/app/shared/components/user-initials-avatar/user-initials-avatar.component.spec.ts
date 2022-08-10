import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserInitialsAvatarComponent } from './user-initials-avatar.component';

describe('UserInitialsAvatarComponent', () => {
  let component: UserInitialsAvatarComponent;
  let fixture: ComponentFixture<UserInitialsAvatarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserInitialsAvatarComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserInitialsAvatarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
