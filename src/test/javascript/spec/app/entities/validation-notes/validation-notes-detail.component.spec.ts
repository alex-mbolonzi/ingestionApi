import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IngestionApiTestModule } from '../../../test.module';
import { ValidationNotesDetailComponent } from 'app/entities/validation-notes/validation-notes-detail.component';
import { ValidationNotes } from 'app/shared/model/validation-notes.model';

describe('Component Tests', () => {
  describe('ValidationNotes Management Detail Component', () => {
    let comp: ValidationNotesDetailComponent;
    let fixture: ComponentFixture<ValidationNotesDetailComponent>;
    const route = { data: of({ validationNotes: new ValidationNotes(123) }) } as any as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [ValidationNotesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ValidationNotesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ValidationNotesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.validationNotes).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
