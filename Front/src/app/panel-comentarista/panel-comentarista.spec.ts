import { TestBed } from '@angular/core/testing';
import { AppModule } from '../app-module';
import { PanelComentarista } from './panel-comentarista';

describe('PanelComentarista', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AppModule],
    }).compileComponents();
  });

  it('should create', () => {
    const fixture = TestBed.createComponent(PanelComentarista);
    const component = fixture.componentInstance;
    expect(component).toBeTruthy();
  });
});
