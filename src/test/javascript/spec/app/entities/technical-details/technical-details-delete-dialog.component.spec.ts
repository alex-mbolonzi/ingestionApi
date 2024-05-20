import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IngestionApiTestModule } from '../../../test.module';
import { TechnicalDetailsDeleteDialogComponent } from 'app/entities/technical-details/technical-details-delete-dialog.component';
import { TechnicalDetailsService } from 'app/entities/technical-details/technical-details.service';

describe('Component Tests', () => {
  describe('TechnicalDetails Management Delete Component', () => {
    let comp: TechnicalDetailsDeleteDialogComponent;
    let fixture: ComponentFixture<TechnicalDetailsDeleteDialogComponent>;
    let service: TechnicalDetailsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [TechnicalDetailsDeleteDialogComponent],
      })
        .overrideTemplate(TechnicalDetailsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TechnicalDetailsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TechnicalDetailsService);
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
