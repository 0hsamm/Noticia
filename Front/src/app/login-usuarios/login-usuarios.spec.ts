import { TestBed } from '@angular/core/testing';
import { LoginUsuarios } from './login-usuarios';
import { AppModule } from '../app-module';

describe('LoginUsuarios', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AppModule],
    }).compileComponents();
  });

  it('should create', () => {
    const fixture = TestBed.createComponent(LoginUsuarios);
    const component = fixture.componentInstance;
    expect(component).toBeTruthy();
  });
});
