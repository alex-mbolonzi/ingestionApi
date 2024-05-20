import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IngestionApiTestModule } from '../../../test.module';
import { DatasetIndicationDeleteDialogComponent } from 'app/entities/dataset-indication/dataset-indication-delete-dialog.component';
import { DatasetIndicationService } from 'app/entities/dataset-indication/dataset-indication.service';

describe('Component Tests', () => {
  describe('DatasetIndication Management Delete Component', () => {
    let comp: DatasetIndicationDeleteDialogComponent;
    let fixture: ComponentFixture<DatasetIndicationDeleteDialogComponent>;
    let service: DatasetIndicationService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [DatasetIndicationDeleteDialogComponent],
      })
        .overrideTemplate(DatasetIndicationDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DatasetIndicationDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DatasetIndicationService);
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
