import { TestBed } from '@angular/core/testing';
import { AppModule } from '../app-module';
import { SingInUsuarios } from './sing-in-usuarios';

describe('SingInUsuarios', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AppModule],
    }).compileComponents();
  });

  it('should create', () => {
    const fixture = TestBed.createComponent(SingInUsuarios);
    const component = fixture.componentInstance;
    expect(component).toBeTruthy();
  });
});
