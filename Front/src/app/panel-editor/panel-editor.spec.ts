import { TestBed } from '@angular/core/testing';
import { AppModule } from '../app-module';
import { PanelEditor } from './panel-editor';

describe('PanelEditor', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AppModule],
    }).compileComponents();
  });

  it('should create', () => {
    const fixture = TestBed.createComponent(PanelEditor);
    const component = fixture.componentInstance;
    expect(component).toBeTruthy();
  });
});
