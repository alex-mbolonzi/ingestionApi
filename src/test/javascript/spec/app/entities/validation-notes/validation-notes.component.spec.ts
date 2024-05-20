import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { IngestionApiTestModule } from '../../../test.module';
import { ValidationNotesComponent } from 'app/entities/validation-notes/validation-notes.component';
import { ValidationNotesService } from 'app/entities/validation-notes/validation-notes.service';
import { ValidationNotes } from 'app/shared/model/validation-notes.model';

describe('Component Tests', () => {
  describe('ValidationNotes Management Component', () => {
    let comp: ValidationNotesComponent;
    let fixture: ComponentFixture<ValidationNotesComponent>;
    let service: ValidationNotesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [ValidationNotesComponent],
        providers: [],
      })
        .overrideTemplate(ValidationNotesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ValidationNotesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ValidationNotesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ValidationNotes(123)],
            headers,
          }),
        ),
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.validationNotes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
