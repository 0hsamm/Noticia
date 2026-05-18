import { TestBed } from '@angular/core/testing';
import { AppModule } from '../app-module';
import { PanelUsuarioNormal } from './panel-usuario-normal';

describe('PanelUsuarioNormal', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AppModule],
    }).compileComponents();
  });

  it('should create', () => {
    const fixture = TestBed.createComponent(PanelUsuarioNormal);
    const component = fixture.componentInstance;
    expect(component).toBeTruthy();
  });
});
