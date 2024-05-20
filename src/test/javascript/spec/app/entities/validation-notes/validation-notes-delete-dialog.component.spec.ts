import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IngestionApiTestModule } from '../../../test.module';
import { ValidationNotesDeleteDialogComponent } from 'app/entities/validation-notes/validation-notes-delete-dialog.component';
import { ValidationNotesService } from 'app/entities/validation-notes/validation-notes.service';

describe('Component Tests', () => {
  describe('ValidationNotes Management Delete Component', () => {
    let comp: ValidationNotesDeleteDialogComponent;
    let fixture: ComponentFixture<ValidationNotesDeleteDialogComponent>;
    let service: ValidationNotesService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [ValidationNotesDeleteDialogComponent],
      })
        .overrideTemplate(ValidationNotesDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ValidationNotesDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ValidationNotesService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        }),
      ));
    });
  });
});
