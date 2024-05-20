import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { IngestionApiTestModule } from '../../../test.module';
import { ValidationNotesUpdateComponent } from 'app/entities/validation-notes/validation-notes-update.component';
import { ValidationNotesService } from 'app/entities/validation-notes/validation-notes.service';
import { ValidationNotes } from 'app/shared/model/validation-notes.model';

describe('Component Tests', () => {
  describe('ValidationNotes Management Update Component', () => {
    let comp: ValidationNotesUpdateComponent;
    let fixture: ComponentFixture<ValidationNotesUpdateComponent>;
    let service: ValidationNotesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [ValidationNotesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ValidationNotesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ValidationNotesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ValidationNotesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ValidationNotes(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ValidationNotes();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
