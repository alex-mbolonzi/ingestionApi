import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IngestionApiTestModule } from '../../../test.module';
import { DatasetDetailsDeleteDialogComponent } from 'app/entities/dataset-details/dataset-details-delete-dialog.component';
import { DatasetDetailsService } from 'app/entities/dataset-details/dataset-details.service';

describe('Component Tests', () => {
  describe('DatasetDetails Management Delete Component', () => {
    let comp: DatasetDetailsDeleteDialogComponent;
    let fixture: ComponentFixture<DatasetDetailsDeleteDialogComponent>;
    let service: DatasetDetailsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [DatasetDetailsDeleteDialogComponent],
      })
        .overrideTemplate(DatasetDetailsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DatasetDetailsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DatasetDetailsService);
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
