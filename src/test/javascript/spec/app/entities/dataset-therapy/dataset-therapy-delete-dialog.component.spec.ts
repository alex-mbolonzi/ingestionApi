import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IngestionApiTestModule } from '../../../test.module';
import { DatasetTherapyDeleteDialogComponent } from 'app/entities/dataset-therapy/dataset-therapy-delete-dialog.component';
import { DatasetTherapyService } from 'app/entities/dataset-therapy/dataset-therapy.service';

describe('Component Tests', () => {
  describe('DatasetTherapy Management Delete Component', () => {
    let comp: DatasetTherapyDeleteDialogComponent;
    let fixture: ComponentFixture<DatasetTherapyDeleteDialogComponent>;
    let service: DatasetTherapyService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [DatasetTherapyDeleteDialogComponent],
      })
        .overrideTemplate(DatasetTherapyDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DatasetTherapyDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DatasetTherapyService);
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
