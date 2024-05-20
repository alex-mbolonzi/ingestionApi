import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IngestionApiTestModule } from '../../../test.module';
import { RequestStatusDetailsDeleteDialogComponent } from 'app/entities/request-status-details/request-status-details-delete-dialog.component';
import { RequestStatusDetailsService } from 'app/entities/request-status-details/request-status-details.service';

describe('Component Tests', () => {
  describe('RequestStatusDetails Management Delete Component', () => {
    let comp: RequestStatusDetailsDeleteDialogComponent;
    let fixture: ComponentFixture<RequestStatusDetailsDeleteDialogComponent>;
    let service: RequestStatusDetailsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [RequestStatusDetailsDeleteDialogComponent],
      })
        .overrideTemplate(RequestStatusDetailsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RequestStatusDetailsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RequestStatusDetailsService);
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
