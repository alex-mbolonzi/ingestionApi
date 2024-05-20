import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IngestionApiTestModule } from '../../../test.module';
import { DatasetStudyDeleteDialogComponent } from 'app/entities/dataset-study/dataset-study-delete-dialog.component';
import { DatasetStudyService } from 'app/entities/dataset-study/dataset-study.service';

describe('Component Tests', () => {
  describe('DatasetStudy Management Delete Component', () => {
    let comp: DatasetStudyDeleteDialogComponent;
    let fixture: ComponentFixture<DatasetStudyDeleteDialogComponent>;
    let service: DatasetStudyService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [DatasetStudyDeleteDialogComponent],
      })
        .overrideTemplate(DatasetStudyDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DatasetStudyDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DatasetStudyService);
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
