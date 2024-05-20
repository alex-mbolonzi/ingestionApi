import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IngestionApiTestModule } from '../../../test.module';
import { IngestionRequestDetailsDeleteDialogComponent } from 'app/entities/ingestion-request-details/ingestion-request-details-delete-dialog.component';
import { IngestionRequestDetailsService } from 'app/entities/ingestion-request-details/ingestion-request-details.service';

describe('Component Tests', () => {
  describe('IngestionRequestDetails Management Delete Component', () => {
    let comp: IngestionRequestDetailsDeleteDialogComponent;
    let fixture: ComponentFixture<IngestionRequestDetailsDeleteDialogComponent>;
    let service: IngestionRequestDetailsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [IngestionRequestDetailsDeleteDialogComponent],
      })
        .overrideTemplate(IngestionRequestDetailsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IngestionRequestDetailsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IngestionRequestDetailsService);
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
