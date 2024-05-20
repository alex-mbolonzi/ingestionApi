import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IngestionApiTestModule } from '../../../test.module';
import { ApplicationReferenceTableDeleteDialogComponent } from 'app/entities/application-reference-table/application-reference-table-delete-dialog.component';
import { ApplicationReferenceTableService } from 'app/entities/application-reference-table/application-reference-table.service';

describe('Component Tests', () => {
  describe('ApplicationReferenceTable Management Delete Component', () => {
    let comp: ApplicationReferenceTableDeleteDialogComponent;
    let fixture: ComponentFixture<ApplicationReferenceTableDeleteDialogComponent>;
    let service: ApplicationReferenceTableService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [ApplicationReferenceTableDeleteDialogComponent],
      })
        .overrideTemplate(ApplicationReferenceTableDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ApplicationReferenceTableDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApplicationReferenceTableService);
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
